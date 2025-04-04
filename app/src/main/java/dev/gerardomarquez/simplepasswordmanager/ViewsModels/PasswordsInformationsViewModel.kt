package dev.gerardomarquez.simplepasswordmanager.ViewsModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.gerardomarquez.simplepasswordmanager.ListStatePaswordInformation
import dev.gerardomarquez.simplepasswordmanager.StatePaswordInformation
import dev.gerardomarquez.simplepasswordmanager.dao.PasswordsInformationsDao
import dev.gerardomarquez.simplepasswordmanager.entities.PasswordsInformations
import dev.gerardomarquez.simplepasswordmanager.repositories.AllFilters
import dev.gerardomarquez.simplepasswordmanager.repositories.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * Clase que representa el ViewModel de la vista Main, se esta ocupando el patron arquitectonico
 * MVVM (Model-View-ViewModel)
 */
class PasswordsInformationsViewModel(
    private val passwordInformationDao: PasswordsInformationsDao,
    context: Context
) : ViewModel() {
    /**
     * Estado que contiene todos los datos de la vista Main
     */
    var state by mutableStateOf(ListStatePaswordInformation())
        private set

    /**
     * Estado que contiene todos los datos de la configuracion de los filtros
     */
    var stateFilters by mutableStateOf(AllFilters())
        private set

    init {
        viewModelScope.launch {
            getAllPasswordsInformations()
            selectedAllFilters(context)
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

    fun selectedOnePasswordInformation(id: Int){
        viewModelScope.launch {
            if(id != state.selectedPassword.id) {
                val passwordInformation = passwordInformationDao.getOnePasswordsInformationsById(id)
                state = state.copy(
                    selectedPassword = passwordInformation
                )
            }
        }
    }

    /**
     * Metodo para cambiar solo el titulo del password
     * @param id del password
     * @param nuevo titulo
     */
    fun changeTitle(id: Int, title: String) {
        viewModelScope.launch {
            lateinit var passwordInformation: PasswordsInformations
            if(id != state.selectedPassword.id) {
                passwordInformation = passwordInformationDao.getOnePasswordsInformationsById(id).copy(
                    password_title = title
                )
            } else {
                passwordInformation = state.selectedPassword.copy(
                    password_title = title
                )
            }
            state = state.copy(
                listPaswordInformation = state.listPaswordInformation,
                selectedPassword = passwordInformation
            )
        }
    }

    /**
     * Metodo para cambiar solo el usuario del password
     * @param id del password
     * @param nuevo usuario
     */
    fun changeUsername(id: Int, username: String) {
        viewModelScope.launch {
            lateinit var passwordInformation: PasswordsInformations
            if(id != state.selectedPassword.id) {
                passwordInformation = passwordInformationDao.getOnePasswordsInformationsById(id).copy(
                    username = username
                )
            } else {
                passwordInformation = state.selectedPassword.copy(
                    username = username
                )
            }
            state = state.copy(
                listPaswordInformation = state.listPaswordInformation,
                selectedPassword = passwordInformation
            )
        }
    }

    /**
     * Metodo para cambiar solo el password del objeto de infromacion del password
     * @param id del password
     * @param nuevo password
     */
    fun changePassword(id: Int, password: String) {
        viewModelScope.launch {
            lateinit var passwordInformation: PasswordsInformations
            if(id != state.selectedPassword.id) {
                passwordInformation = passwordInformationDao.getOnePasswordsInformationsById(id).copy(
                    password = password
                )
            } else {
                passwordInformation = state.selectedPassword.copy(
                    password = password
                )
            }
            state = state.copy(
                listPaswordInformation = state.listPaswordInformation,
                selectedPassword = passwordInformation
            )
        }
    }

    /**
     * Metodo para cambiar solo el token del objeto de infromacion del password
     * @param id del password
     * @param nuevo token
     */
    fun changeToken(id: Int, token: String) {
        viewModelScope.launch {
            lateinit var passwordInformation: PasswordsInformations
            if(id != state.selectedPassword.id) {
                passwordInformation = passwordInformationDao.getOnePasswordsInformationsById(id).copy(
                    token = token
                )
            } else {
                passwordInformation = state.selectedPassword.copy(
                    token = token
                )
            }
            state = state.copy(
                listPaswordInformation = state.listPaswordInformation,
                selectedPassword = passwordInformation
            )
        }
    }

    /**
     * Metodo para cambiar solo el email del objeto de infromacion del password
     * @param id del password
     * @param nuevo email
     */
    fun changeEmail(id: Int, email: String) {
        viewModelScope.launch {
            lateinit var passwordInformation: PasswordsInformations
            if(id != state.selectedPassword.id) {
                passwordInformation = passwordInformationDao.getOnePasswordsInformationsById(id).copy(
                    email = email
                )
            } else {
                passwordInformation = state.selectedPassword.copy(
                    email = email
                )
            }
            state = state.copy(
                listPaswordInformation = state.listPaswordInformation,
                selectedPassword = passwordInformation
            )
        }
    }

    /**
     * Metodo para cambiar solo el phone del objeto de infromacion del password
     * @param id del password
     * @param nuevo numero de telefono
     */
    fun changePhoneNumber(id: Int, phoneNumber: String) {
        viewModelScope.launch {
            lateinit var passwordInformation: PasswordsInformations
            if(id != state.selectedPassword.id) {
                passwordInformation = passwordInformationDao.getOnePasswordsInformationsById(id).copy(
                    phone =  phoneNumber.toLongOrNull()
                )
            } else {
                passwordInformation = state.selectedPassword.copy(
                    phone =  phoneNumber.toLongOrNull()
                )
            }
            state = state.copy(
                listPaswordInformation = state.listPaswordInformation,
                selectedPassword = passwordInformation
            )
        }
    }

    /**
     * Metodo para cambiar solo el url del objeto de infromacion del password
     * @param id del password
     * @param nueva pagina web
     */
    fun changeWebPage(id: Int, url: String) {
        viewModelScope.launch {
            lateinit var passwordInformation: PasswordsInformations
            if(id != state.selectedPassword.id) {
                passwordInformation = passwordInformationDao.getOnePasswordsInformationsById(id).copy(
                    url =  url
                )
            } else {
                passwordInformation = state.selectedPassword.copy(
                    url =  url
                )
            }
            state = state.copy(
                listPaswordInformation = state.listPaswordInformation,
                selectedPassword = passwordInformation
            )
        }
    }

    /**
     * Metodo para cambiar solo el notes del objeto de infromacion del password
     * @param id del password
     * @param nuevoas notas
     */
    fun changeNotes(id: Int, notes: String) {
        viewModelScope.launch {
            lateinit var passwordInformation: PasswordsInformations
            if(id != state.selectedPassword.id) {
                passwordInformation = passwordInformationDao.getOnePasswordsInformationsById(id).copy(
                    notes =  notes
                )
            } else {
                passwordInformation = state.selectedPassword.copy(
                    notes =  notes
                )
            }
            state = state.copy(
                listPaswordInformation = state.listPaswordInformation,
                selectedPassword = passwordInformation
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

    /**
     * Metodo que selecciona la configuracion actual de la aplicacion
     * @param context Contexto de la aplicacion
     */
    private fun selectedAllFilters(context: Context){
        viewModelScope.launch {
            val filters: AllFilters = SettingsDataStore.getFilters(context = context)
            stateFilters = stateFilters.copy(
                title = filters.title,
                user = filters.user,
                phone = filters.phone,
                email = filters.email,
                notes = filters.notes,
                url = filters.url
            )

        }
    }

    /**
     * Cambia la configuracion del usuario que el usuario chequeó
     * @param title Filtro para buscar por el usuario en el caso de que sea true
     */
    fun changeFilterUser(
        user: Boolean
    ){
        viewModelScope.launch {
            if(stateFilters.user != user) {
                stateFilters = stateFilters.copy(
                    user = user
                )
            }
        }
    }

    /**
     * Cambia la configuracion de los comentarios que el usuario chequeó
     * @param notes Filtro para buscar por el comentarios en el caso de que sea true
     */
    fun changeFilterNotes(
        notes: Boolean
    ){
        viewModelScope.launch {
            if(stateFilters.notes != notes) {
                stateFilters = stateFilters.copy(
                    notes = notes
                )
            }
        }
    }
    /**
     * Cambia la configuracion de la url que el usuario chequeó
     * @param url Filtro para buscar por la url en el caso de que sea true
     */
    fun changeFilterUrl(
        url: Boolean
    ){
        viewModelScope.launch {
            if(stateFilters.url != url) {
                stateFilters = stateFilters.copy(
                    url = url
                )
            }
        }
    }

    /**
     * Cambia la configuracion del email que el usuario chequeó
     * @param email Filtro para buscar por el email en el caso de que sea true
     */
    fun changeFilterEmail(
        email: Boolean
    ){
        viewModelScope.launch {
            if(stateFilters.email != email) {
                stateFilters = stateFilters.copy(
                    email = email
                )
            }
        }
    }

    /**
     * Cambia la configuracion del numero que el usuario chequeó
     * @param number Filtro para buscar por el numero telefonico en el caso de que sea true
     */
    fun changeFilterNumber(
        number: Boolean
    ){
        viewModelScope.launch {
            if(stateFilters.phone != number) {
                stateFilters = stateFilters.copy(
                    phone = number
                )
            }
        }
    }

    /**
     * Cambia la configuracion del titulo que el usuario chequeó
     * @param title Filtro para buscar por el titulo en el caso de que sea true
     */
    fun changeFilterTitle(
        title: Boolean
    ){
        viewModelScope.launch {
            if(stateFilters.title != title) {
                stateFilters = stateFilters.copy(
                    title = title
                )
            }
        }
    }

    /**
     * Guarda todos los filtros configurados en el stateFilters
     */
    fun saveAllFilters(context: Context){
        viewModelScope.launch {
            SettingsDataStore.saveFilters(
                context = context,
                filtersConfigurations = stateFilters.copy()
            )
        }
    }
}