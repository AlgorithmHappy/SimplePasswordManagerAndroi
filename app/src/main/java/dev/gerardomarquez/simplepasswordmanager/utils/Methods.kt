package dev.gerardomarquez.simplepasswordmanager.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import java.io.File

/*
* Metodo que obtiene todas las carpetas que se encuentran en el directorio que se le pase
* @param path Ruta del directorio que se quiere obtener las carpetas
* @return Lista de nombres de carpetas que se encuentran en el directorio
 */
fun getFolders(path: String = Environment.getExternalStorageDirectory().absolutePath): List<String> {
    val directory = File(path)
    return directory.listFiles()?.filter { it.isDirectory }?.map { it.name } ?: emptyList()
}

/*
* Metodo que obtiene todos los archivos con extension "db" de la carpeta que se le pase
* @param path Ruta del directorio que se quiere obtener las carpetas
* @return Lista de nombres de carpetas y de archivos con su extension (.db) que se encuentran en el directorio
 */
fun getFiles(path: String = Environment.getExternalStorageDirectory().absolutePath, context: Context): List<String> {

    val directory = File(path)
    val dbFile = File(directory, "pruebaXDXDXD.db")
    Log.d("FileDebug", "El archivo específico existe: ${dbFile.exists()}")

    return directory
        .listFiles()?.filter {
            it.isFile && it.extension.equals(Constants.GLOBAL_STR_DATABASE_EXTENSION)
        }?.map { it.name + Constants.GLOBAL_STR_DOT + it.extension } ?: emptyList()
}