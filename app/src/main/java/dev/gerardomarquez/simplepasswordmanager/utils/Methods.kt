package dev.gerardomarquez.simplepasswordmanager.utils

import android.os.Environment
import java.io.File

/*
* Metodo que obtiene todas las carpetas que se encuentran en el directorio que se le pase
* @param path Ruta del directorio que se quiere obtener las carpetas
* @return Lista de nombres de carpetas que se encuentran en el directorio
 */
fun getFolders(path: String = Environment.getExternalStorageDirectory().absolutePath): List<String> {
    val directory = File(path)
    return directory.listFiles()?.filter { it.isDirectory }?.map { it.name } ?: emptyList() // Filtra solo los directorios
}