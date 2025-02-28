package dev.gerardomarquez.simplepasswordmanager.navigations

/**
 * Clase que gestiona todos los ids de las pantallas de la aplicacion
 * @param route Id de la pantalla
 */
sealed class Routes(val route: String) {
    object ScreenLogin: Routes(route = "login")
    object ScreenFolderFileExplorer: Routes(route = "folder_file_explorer")
    object ScreenNewFileExplorer: Routes(route = "new_file_explorer")
    object ScreenMain: Routes(route = "main")
    object ScreenInsertPassword: Routes(route = "insert_password")
    object ScreenUpdatePassword: Routes(route = "update_password")
    object ScreenFilters: Routes(route = "filters")
}