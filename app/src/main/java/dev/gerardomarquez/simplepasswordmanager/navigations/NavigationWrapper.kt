package dev.gerardomarquez.simplepasswordmanager.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.gerardomarquez.simplepasswordmanager.views.DataInsert
import dev.gerardomarquez.simplepasswordmanager.views.DataUpdate
import dev.gerardomarquez.simplepasswordmanager.views.Filters
import dev.gerardomarquez.simplepasswordmanager.views.Login
import dev.gerardomarquez.simplepasswordmanager.views.Main
import dev.gerardomarquez.simplepasswordmanager.views.NewFileExplorerView
import dev.gerardomarquez.simplepasswordmanager.views.OpenFileExplorerView

/**
 * Clase que gestionara la navegacion entre pantallas de la aplicacion
 * @param modifier Modificador que se aplicara a todas las pantallas que se agreguen
 */
@Composable
fun NavigationWrapper(modifier: Modifier){
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = ScreLogin
    ){
        composable<ScreLogin>{
            Login(
                modifier = modifier,
                navigateToMain = {
                    navigationController.navigate(ScreMain)
                },
                navigateToFolderFileExplorer = {
                    selectedFolderString ->
                    navigationController.navigate(ScreFolderFileExplorer(selectedFolderString = selectedFolderString) )
                },
                navigateToNewFileExplorer = {
                    navigationController.navigate(ScreNewFileExplorer)
                }
            )
        }
        composable<ScreMain> {
            Main(
                modifier = modifier,
                navigateToFilters = {
                    navigationController.navigate(ScreFilters)
                },
                navigateToInsert = {
                    navigationController.navigate(ScreInsertPassword)
                },
                navigateToUpdate = {
                    navigationController.navigate(ScreUpdatePassword)
                }
            )
        }
        composable<ScreNewFileExplorer> {
            NewFileExplorerView(
                modifier = modifier,
                navigateToLogin = {
                    navigationController.popBackStack()
                }
            )
        }
        composable<ScreFolderFileExplorer> {
            backStackEntry ->
            val folderFileExplorer: ScreFolderFileExplorer = backStackEntry.toRoute()
            OpenFileExplorerView(
                modifier = modifier,
                selectedFolderString = folderFileExplorer.selectedFolderString,
                navigateToLogin = {
                    navigationController.popBackStack()
                }
            )
        }
        composable<ScreFilters> {
            Filters(
                modifier = modifier,
                navigateToMain = {
                    navigationController.popBackStack()
                }
            )
        }
        composable<ScreInsertPassword> {
            DataInsert(
                modifier = modifier,
                navigateToMain = {
                    navigationController.popBackStack()
                }
            )
        }
        composable<ScreUpdatePassword> {
            DataUpdate(
                modifier = modifier,
                navigateToMain = {
                    navigationController.popBackStack()
                }
            )
        }
    }
}