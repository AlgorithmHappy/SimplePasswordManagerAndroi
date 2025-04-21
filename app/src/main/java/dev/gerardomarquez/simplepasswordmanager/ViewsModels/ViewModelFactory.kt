package dev.gerardomarquez.simplepasswordmanager.ViewsModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.gerardomarquez.simplepasswordmanager.ViewsModels.PasswordsInformationsViewModel
import dev.gerardomarquez.simplepasswordmanager.dao.PasswordsInformationsDao

class PasswordsInformationsViewModelFactory(
    private val dao: PasswordsInformationsDao,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordsInformationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasswordsInformationsViewModel(dao, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
