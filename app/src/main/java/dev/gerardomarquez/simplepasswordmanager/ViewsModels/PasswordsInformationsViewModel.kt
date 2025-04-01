package dev.gerardomarquez.simplepasswordmanager.ViewsModels

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dev.gerardomarquez.simplepasswordmanager.StatePaswordInformation
import dev.gerardomarquez.simplepasswordmanager.dao.PasswordsInformationsDao
import dev.gerardomarquez.simplepasswordmanager.entities.PasswordsInformations
import kotlinx.coroutines.launch

/**
 * Clase que representa el ViewModel de la vista Main, se esta ocupando el patron arquitectonico
 * MVVM (Model-View-ViewModel)
 */
class PasswordsInformationsViewModel(
    private val paswordInformationDao: PasswordsInformationsDao
) : ViewModel() {
    /**
     * Estado que contiene todos los datos de la vista Main
     */
    var state by mutableStateOf(listOf<StatePaswordInformation>() )
        private set

    /**
     * Metodo que obtiene todos los datos de la tabla "passwords_informations" de la base de datos
     */
    fun getAllPasswordsInformations() {
        viewModelScope.launch {
            state = paswordInformationDao.getAllPasswordsInformations().map {
                StatePaswordInformation(
                    id = it.id,
                    password_title = it.password_title,
                    username = it.username,
                    password = it.password,
                    token = it.token,
                    email = it.email,
                    phone = it.phone,
                    url = it.url,
                    notes = it.notes
                )
            }
        }
    }

    /**
     * Metodo que guarda un objeto de tipo PasswordsInformations en la tabla "passwords_informations" de la base de datos
     * con este mismo se puede actualizar, en la actualizacion se le hara caso al id
     * @param objeto Entity para la llenar la tabla "passwords_informations"
     */
    fun saveOnePasswordInformation(passwordInformation: PasswordsInformations){
        viewModelScope.launch {
            paswordInformationDao.insertOnePasswordInformation(
                passwordInformation
            )
            getAllPasswordsInformations()
        }
    }

    /**
     * Metodo que elimina un objeto de tipo PasswordsInformations de la tabla "passwords_informations" de la base de datos
     * @param objeto Entity para eliminar de la tabla "passwords_informations"
     */
    fun deleteOnePasswordInformation(passwordInformation: PasswordsInformations){
        viewModelScope.launch {
            paswordInformationDao.deletePasswordInformation(
                passwordInformation
            )
            getAllPasswordsInformations()
        }
    }
}