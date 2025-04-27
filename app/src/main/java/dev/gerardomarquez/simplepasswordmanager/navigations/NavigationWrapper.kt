package dev.gerardomarquez.simplepasswordmanager.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.gerardomarquez.simplepasswordmanager.ViewsModels.PasswordsInformationsViewModel
import dev.gerardomarquez.simplepasswordmanager.views.DataInsert
import dev.gerardomarquez.simplepasswordmanager.views.DataUpdate
import dev.gerardomarquez.simplepasswordmanager.views.Filters
import dev.gerardomarquez.simplepasswordmanager.views.Login
import dev.gerardomarquez.simplepasswordmanager.views.Main
import dev.gerardomarquez.simplepasswordmanager.views.NewFileExplorerView
import dev.gerardomarquez.simplepasswordmanager.views.OpenFileExplorerView
import dev.gerardomarquez.simplepasswordmanager.views.Settings

/**
 * Clase que gestionara la navegacion entre pantallas de la aplicacion
 * @param modifier Modificador que se aplicara a todas las pantallas que se agreguen
 */
@Composable
fun NavigationWrapper(modifier: Modifier, viewModel: PasswordsInformationsViewModel){
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = Login
    ){
        composable<Login>{
            Login(
                modifier = modifier,
                viewModel = viewModel,
                navigateToMain = {
                    navigationController.navigate(Main)
                },
                navigateToFolderFileExplorer = {
                    selectedFolderString ->
                    navigationController.navigate(FolderFileExplorer(selectedFolderString = selectedFolderString) )
                },
                navigateToNewFileExplorer = {
                    navigationController.navigate(NewFileExplorer)
                },
                navigateToSettings = {
                    navigationController.navigate(Settings)
                }
            )
        }
        composable<Main> {
            Main(
                modifier = modifier,
                viewModel = viewModel,
                navigateToFilters = {
                    navigationController.navigate(Filters)
                },
                navigateToInsert = {
                    navigationController.navigate(InsertPassword)
                },
                navigateToLogin = {
                    navigationController.navigate(Login)
                },
                navigateToUpdate = {
                    idPasswordInformation ->
                    navigationController.navigate(UpdatePassword(idPasswordInformation = idPasswordInformation) )
                }
            )
        }
        composable<NewFileExplorer> {
            NewFileExplorerView(
                modifier = modifier,
                viewModel = viewModel,
                navigateToMain = {
                    navigationController.navigate(Main)
                },
                navigateToLogin = {
                    navigationController.popBackStack()
                }
            )
        }
        composable<FolderFileExplorer> {
            backStackEntry ->
            val folderFileExplorer: FolderFileExplorer = backStackEntry.toRoute()
            OpenFileExplorerView(
                modifier = modifier,
                viewModel = viewModel,
                selectedFolderString = folderFileExplorer.selectedFolderString,
                navigateToLogin = {
                    navigationController.popBackStack()
                }
            )
        }
        composable<Filters> {
            Filters(
                modifier = modifier,
                viewModel = viewModel,
                navigateToMain = {
                    navigationController.popBackStack()
                }
            )
        }
        composable<InsertPassword> {
            DataInsert(
                modifier = modifier,
                viewModel = viewModel,
                navigateToMain = {
                    navigationController.popBackStack()
                }
            )
        }
        composable<UpdatePassword> {
            backStackEntry ->
            val idPasswordInformation: UpdatePassword = backStackEntry.toRoute()
            DataUpdate(
                modifier = modifier,
                viewModel = viewModel,
                idPasswordInformation = idPasswordInformation.idPasswordInformation,
                navigateToMain = {
                    navigationController.popBackStack()
                }
            )
        }
        composable<Settings> {
            Settings(
                modifier = modifier,
                viewModel = viewModel,
                navigateToLogin = {
                    navigationController.popBackStack()
                }
            )
        }
    }
}