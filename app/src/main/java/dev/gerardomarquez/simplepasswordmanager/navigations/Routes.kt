package dev.gerardomarquez.simplepasswordmanager.navigations

import kotlinx.serialization.Serializable

/**
 * Clase que contiene la ruta de la base de datos nueva que se creo, o de una base de datos que el
 * combobox no tenia y que se agrego
 * @param pathNewFile Ruta de la base de datos nueva que se creo
 * @param pathFolderFile Ruta de la base de datos que se agrego
 */
@Serializable
//data class ScreLogin(val pathNewFile: String = "", val pathFolderFile: String = "")
object ScreLogin

@Serializable
object ScreFolderFileExplorer

@Serializable
object ScreNewFileExplorer

@Serializable
object ScreMain

@Serializable
object ScreInsertPassword

@Serializable
object ScreUpdatePassword

@Serializable
object ScreFilters
