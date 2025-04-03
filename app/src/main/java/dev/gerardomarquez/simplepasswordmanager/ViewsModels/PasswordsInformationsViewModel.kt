package dev.gerardomarquez.simplepasswordmanager.ViewsModels

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dev.gerardomarquez.simplepasswordmanager.ListStatePaswordInformation
import dev.gerardomarquez.simplepasswordmanager.StatePaswordInformation
import dev.gerardomarquez.simplepasswordmanager.dao.PasswordsInformationsDao
import dev.gerardomarquez.simplepasswordmanager.entities.PasswordsInformations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * Clase que representa el ViewModel de la vista Main, se esta ocupando el patron arquitectonico
 * MVVM (Model-View-ViewModel)
 */
class PasswordsInformationsViewModel(
    private val passwordInformationDao: PasswordsInformationsDao
) : ViewModel() {
    /**
     * Estado que contiene todos los datos de la vista Main
     */
    var state by mutableStateOf(ListStatePaswordInformation())
        private set

    var selectedPassword by mutableStateOf(PasswordsInformations(
        id = 0,
        password_title = "",
        username = "",
        password = "",
        token = null,
        email = null,
        phone = null,
        url = null,
        notes = null
    ))
        private set

    init {
        viewModelScope.launch {
            getAllPasswordsInformations()
        }
    }

    /**
     * Metodo que obtiene todos los objetos de tipo PasswordsInformations de la tabla "passwords_informations"
     * de la base de datos
     */
    fun getAllPasswordsInformations(){
        viewModelScope.launch {
            var listPaswordInformation: List<StatePaswordInformation> = passwordInformationDao.getAllPasswordsInformations().map {
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
            state = state.copy(
                listPaswordInformation = listPaswordInformation,
                ready = true
            )
        }
    }

    fun getOnePasswordInformationById(id: Int) {
        viewModelScope.launch {
            val passwordInformation: PasswordsInformations = passwordInformationDao.getOnePasswordsInformationsById(id)
            selectedPassword = selectedPassword.copy(
                id = passwordInformation.id,
                password_title = passwordInformation.password_title,
                username = passwordInformation.username,
                password = passwordInformation.password,
                token = passwordInformation.token,
                email = passwordInformation.email,
                phone = passwordInformation.phone,
                url = passwordInformation.url,
                notes = passwordInformation.notes
            )
        }
    }

    /**
     * Metodo que guarda un objeto de tipo PasswordsInformations en la tabla "passwords_informations" de la base de datos
     * con este mismo se puede actualizar, en la actualizacion se le hara caso al id
     * @param objeto Entity para la llenar la tabla "passwords_informations"
     */
    fun saveOnePasswordInformation(passwordInformation: PasswordsInformations){
        viewModelScope.launch {
            passwordInformationDao.insertOnePasswordInformation(
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
            passwordInformationDao.deletePasswordInformation(
                passwordInformation
            )
            getAllPasswordsInformations()
        }
    }
}