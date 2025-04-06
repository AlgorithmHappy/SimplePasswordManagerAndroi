package dev.gerardomarquez.simplepasswordmanager.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.documentfile.provider.DocumentFile
import java.io.File

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
