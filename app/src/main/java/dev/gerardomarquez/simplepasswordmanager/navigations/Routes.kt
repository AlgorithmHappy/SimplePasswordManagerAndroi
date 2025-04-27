package dev.gerardomarquez.simplepasswordmanager.navigations

import kotlinx.serialization.Serializable

/**
 * Clase que contiene la ruta de la base de datos nueva que se creo, o de una base de datos que el
 * combobox no tenia y que se agrego
 * @param pathNewFile Ruta de la base de datos nueva que se creo
 * @param pathFolderFile Ruta de la base de datos que se agrego
 */
@Serializable
object Login

@Serializable
data class FolderFileExplorer(val selectedFolderString: String)

@Serializable
object NewFileExplorer

@Serializable
object Main

@Serializable
object InsertPassword

@Serializable
data class UpdatePassword(val idPasswordInformation: Int)

@Serializable
object Filters

@Serializable
object Settings
