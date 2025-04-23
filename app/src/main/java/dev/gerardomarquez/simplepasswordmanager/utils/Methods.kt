package dev.gerardomarquez.simplepasswordmanager.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.documentfile.provider.DocumentFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2KtResult
import com.lambdapioneer.argon2kt.Argon2Mode
import java.security.SecureRandom

/**
* Metodo que obtiene todas las carpetas que se encuentran en el directorio que se le pase
* @param path Ruta del directorio que se quiere obtener las carpetas
* @return Lista de nombres de carpetas que se encuentran en el directorio
 */
fun getFolders(path: String = Environment.getExternalStorageDirectory().absolutePath): List<String> {
    val directory = File(path)
    return directory.listFiles()?.filter { it.isDirectory }?.map { it.name } ?: emptyList()
}

/**
* Metodo que obtiene todos los archivos con extension "db" de la carpeta que se le pase
* @param path Ruta del directorio que se quiere obtener las carpetas
* @return Lista de nombres de carpetas y de archivos con su extension (.db) que se encuentran en el directorio
 */
fun getFiles(context: Context, uri: Uri?): List<String?> {
    if (uri == null || uri.toString().isBlank() ) return emptyList()  // Si el Uri es nulo o vacia, retornamos una lista vacía
    val docFile = DocumentFile.fromTreeUri(context, uri)  // Convertimos el Uri en un DocumentFile
    return docFile?.listFiles()?.map {
        it -> it.name
    }?.filter {
        it -> it!!.endsWith(suffix = Constants.GLOBAL_STR_DOT + Constants.GLOBAL_STR_DATABASE_EXTENSION )
    }?.toList() ?: emptyList()  // Obtenemos la lista de archivos
}

/**
 * Metodo para generar contraseñas aleatorias
 * @param size Tamaño de la contraseña
 * @param charactersIncluded Caracteres que se podran incluir en la contraseña
 */
fun passwordGenerator(
    size: Int,
    charactersIncluded: CharactersIncludedInPassword
): String {

    var allCharacters: String = ""
    var requiredCharacters: String = ""
    if (charactersIncluded.lowerCase){
        allCharacters += Constants.TXT_LOWER_CASE
        requiredCharacters += Constants.TXT_LOWER_CASE.random()
    }
    if (charactersIncluded.upperCase){
        allCharacters += Constants.TXT_UPPER_CASE
        requiredCharacters += Constants.TXT_UPPER_CASE.random()
    }
    if (charactersIncluded.numbers){
        allCharacters += Constants.TXT_NUMBERS
        requiredCharacters += Constants.TXT_NUMBERS.random()
    }
    if (charactersIncluded.specialCharacters){
        allCharacters += Constants.TXT_SPECIAL_CHARACTERS
        requiredCharacters += Constants.TXT_SPECIAL_CHARACTERS.random()
    }

    var passwordIncompleted: String = (1..size-requiredCharacters.length)
        .map { allCharacters.random() }
        .joinToString("")
    passwordIncompleted += requiredCharacters
    return passwordIncompleted.toList().shuffled().joinToString("") // Desordenamos la contraseña en una forma aleatoria
}

/**
 * Clase que indica quecaracteres podra tener la contraseña
 */
data class CharactersIncludedInPassword (
    val lowerCase: Boolean = false,
    val upperCase: Boolean = false,
    val numbers: Boolean = false,
    val specialCharacters: Boolean = false
) {
    init {
        if(!lowerCase && !upperCase && !numbers && !specialCharacters) {
            throw IllegalArgumentException(Constants.TXT_ERROR_CHRACTER_TYPE)
        }
    }
}

/**
 * Metodo para encriptar la base de datos creada
 * @param inputDataBaseFile Archivo .db de la base de datos en claro
 * @param outputFile Archivo .db encriptado
 * @param secretKey Password que introdujo el usuario pero ya transformado con Argon2 para ser valido
 */
fun encryptDatabaseFile(
    inputDataBaseFile: File,
    outputDatabaseFile: File,
    secretKey: SecretKey
) {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey) // IV aleatorio automático
    val iv = cipher.iv // Obtener el IV generado

    FileOutputStream(outputDatabaseFile).use { output ->
        output.write(iv) // Guardar IV al inicio del archivo
        CipherOutputStream(output, cipher).use { cryptoOut ->
            FileInputStream(inputDataBaseFile).use { input ->
                input.copyTo(cryptoOut)
            }
        }
    }
}

/**
 * Metodo para desencriptar la base de datos creada y que la pueda abrir room
 * @param inputDataBaseFile Archivo .db encriptado
 * @param outputDatabaseFile Archivo .db de la base de datos en claro listo para ser utilizado con room
 * @param secretKey Password que introdujo el usuario pero ya transformado con Argon2 para ser valido
 * @throws Exception Si ocurre un error al desencriptar el archivo (Como por ejemplo si el secretKey no es)
 */
@Throws(
    Exception::class
)
fun decryptDatabaseFile(
    inputDataBaseFile: File,
    outputDatabaseFile: File,
    secretKey: SecretKey
) {
    FileInputStream(inputDataBaseFile).use { input ->
        val iv = ByteArray(16)
        input.read(iv) // Leer IV almacenado
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

        CipherInputStream(input, cipher).use { cryptoIn ->
            FileOutputStream(outputDatabaseFile).use { output ->
                cryptoIn.copyTo(output)
            }
        }
    }
}

/**
 * Metodo para generar un secret key a partir del password que introdujo el usuario
 * @param password Password que introdujo el usuario
 * @param salt Salt que se le pasara al algoritmo Argon2, este salt debera ser diferente por cada
 * base de datos que se cree para mayor seguridad
 */
fun generateSecretKey(password: String, salt: ByteArray): SecretKey {
    val argon2 = Argon2Kt()

    // Configuración segura (ajusta según necesidades):
    val result: Argon2KtResult = argon2.hash(
        mode = Argon2Mode.ARGON2_ID,
        tCostInIterations = 5,
        mCostInKibibyte = 65536,
        parallelism = 4,            // Hilos en paralelo
        password = password.toByteArray(),
        salt = salt,
        hashLengthInBytes = 32             // 32 bytes = clave AES-256
    )

    // Convierte el hash resultante en una SecretKey AES
    return SecretKeySpec(result.rawHashAsByteArray(), "AES")
}

/**
 * Metodo para convertir un String hexadecimal ingresado por el usuario a un ByteArray
 * @param hexString hexadecimial valido para los salts
 */
fun hexStringToByteArray(hexString: String): ByteArray {
    val hexPattern: String = "^[0-9a-fA-F]+\$"
    require(hexString.length == 32) { "El texto hexadecimal debe tener una longitud de 32 caracteres 16 bytes" }
    require(hexString.matches(hexPattern.toRegex() ) ) { "El valor ingresado no es hexadecimal" }

    return hexString.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
}

/**
 * Metodo que genera un archivo de texto con el salt de la base de datos encriptada
 * @param path Ruta donde se guardara el archivo
 * @param salt Salt con el que se encripto la contraseña en claro
 */
fun generateFileSalt(path: String, salt: String){
    val file: File = File(path)
    file.writeText(salt)
}