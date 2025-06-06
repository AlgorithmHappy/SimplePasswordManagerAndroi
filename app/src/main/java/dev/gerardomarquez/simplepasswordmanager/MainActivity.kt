package dev.gerardomarquez.simplepasswordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.gerardomarquez.simplepasswordmanager.ViewsModels.PasswordsInformationsViewModel
import dev.gerardomarquez.simplepasswordmanager.ViewsModels.PasswordsInformationsViewModelFactory
import dev.gerardomarquez.simplepasswordmanager.database.AppDatabase
import dev.gerardomarquez.simplepasswordmanager.navigations.NavigationWrapper
import dev.gerardomarquez.simplepasswordmanager.ui.theme.SimplePasswordManagerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getInstance(applicationContext)
        val dao = database.passwordsInformationsDao()

        setContent {
            val context = LocalContext.current
            val factory = PasswordsInformationsViewModelFactory(dao, context)
            val viewModel: PasswordsInformationsViewModel = viewModel(factory = factory)
            SimplePasswordManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationWrapper(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}