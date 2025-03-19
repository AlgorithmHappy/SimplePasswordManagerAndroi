package dev.gerardomarquez.simplepasswordmanager.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.documentfile.provider.DocumentFile
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
fun getFiles(context: Context, uri: Uri?): List<String?> {
    if (uri == null || uri.toString().isBlank() ) return emptyList()  // Si el Uri es nulo o vacia, retornamos una lista vacía
    val docFile = DocumentFile.fromTreeUri(context, uri)  // Convertimos el Uri en un DocumentFile
    return docFile?.listFiles()?.map {
        it -> it.name
    }?.filter {
        it -> it!!.endsWith(suffix = Constants.GLOBAL_STR_DOT + Constants.GLOBAL_STR_DATABASE_EXTENSION )
    }?.toList() ?: emptyList()  // Obtenemos la lista de archivos
}