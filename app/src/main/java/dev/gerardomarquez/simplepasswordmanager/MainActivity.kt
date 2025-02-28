package dev.gerardomarquez.simplepasswordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.gerardomarquez.simplepasswordmanager.navigations.Routes
import dev.gerardomarquez.simplepasswordmanager.ui.theme.SimplePasswordManagerTheme
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import dev.gerardomarquez.simplepasswordmanager.views.DataInsert
import dev.gerardomarquez.simplepasswordmanager.views.DataUpdate
import dev.gerardomarquez.simplepasswordmanager.views.Filters
import dev.gerardomarquez.simplepasswordmanager.views.Login
import dev.gerardomarquez.simplepasswordmanager.views.Main
import dev.gerardomarquez.simplepasswordmanager.views.NewFileExplorerView
import dev.gerardomarquez.simplepasswordmanager.views.OpenFileExplorerView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimplePasswordManagerTheme {
                val navigationController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.ScreenLogin.route
                    ) {
                        composable(Routes.ScreenLogin.route){
                            Login(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                navigationController = navigationController
                            )
                        }
                        composable(Routes.ScreenNewFileExplorer.route){
                            NewFileExplorerView(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                navigationController = navigationController
                            )
                        }
                        composable(Routes.ScreenFolderFileExplorer.route){
                            OpenFileExplorerView(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                navigationController = navigationController
                            )
                        }
                        composable(Routes.ScreenMain.route){
                            Main(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                navigationController = navigationController
                            )
                        }
                        composable(Routes.ScreenInsertPassword.route){
                            DataInsert(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                navigationController = navigationController
                            )
                        }
                        composable(Routes.ScreenUpdatePassword.route){
                            DataUpdate(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                navigationController = navigationController
                            )
                        }
                        composable(Routes.ScreenFilters.route){
                            Filters(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                navigationController = navigationController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimplePasswordManagerTheme {
        Greeting("Android")
    }
}