package dev.gerardomarquez.simplepasswordmanager.ViewsModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dev.gerardomarquez.simplepasswordmanager.ListStatePaswordInformation
import dev.gerardomarquez.simplepasswordmanager.StatePaswordInformation
import dev.gerardomarquez.simplepasswordmanager.dao.PasswordsInformationsDao
import dev.gerardomarquez.simplepasswordmanager.database.AppDatabase
import dev.gerardomarquez.simplepasswordmanager.entities.PasswordsInformations
import dev.gerardomarquez.simplepasswordmanager.repositories.AllFilters
import dev.gerardomarquez.simplepasswordmanager.repositories.SettingsDataStore
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import dev.gerardomarquez.simplepasswordmanager.utils.decryptDatabaseFile
import dev.gerardomarquez.simplepasswordmanager.utils.encryptDatabaseFile
import dev.gerardomarquez.simplepasswordmanager.utils.generateFileSalt
import dev.gerardomarquez.simplepasswordmanager.utils.generateSecretKey
import dev.gerardomarquez.simplepasswordmanager.utils.hexStringToByteArray
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import java.security.SecureRandom

/**
 * Clase que representa el ViewModel de la vista Main, se esta ocupando el patron arquitectonico
 * MVVM (Model-View-ViewModel)
 */
class PasswordsInformationsViewModel(
    private var passwordInformationDao: PasswordsInformationsDao,
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

    /**
     * Estado que contiene la lista de salt que se han utilizado en la aplicacion
     */
    var stateListSalt by mutableStateOf(emptyList<String>())
        private set

    /**
     * Estado que contiene el utlimo salt que utilizo el usuario
     *///
    var stateSalt by mutableStateOf(String())
        private set

    /**
     * Estado de la contraseña en claro
     */
    var stateClearPasswordDb by mutableStateOf(String())
        private set

    /**
     * Lista de paths de las bases de datos
     */
    var stateListPaths by mutableStateOf(emptyList<String>() )
        private set

    /**
     * Lista de paths de las bases de datos
     */
    var stateSelectedPath by mutableStateOf(String() )
        private set

    /**
     * Lista de nombres de archivos sin extension de la base de datos
     */
    var stateListFilesNames by mutableStateOf(emptyList<String>() )
        private set

    var stateSelectedFileName by mutableStateOf(String() )

    /**
     * Señal que se ejecuta solo una vez para indicar que lanze un Toast de error en la vista
     */
    private val _errorDecryptUi = Channel<Boolean>()

    /**
     * Variable booleana que recive solo una vez la señal de true en caso de error o false si no hubo error
     */
    val errorDecryptUI = _errorDecryptUi.receiveAsFlow()

    /**
     * Variable que cambia para bloquear la pantalla de main si el login es incorrecto
     */
    var stateLockMain by mutableStateOf(true)

    init {
        viewModelScope.launch {
            getAllPasswordsInformations()
            selectedAllFilters(context)
            selectedAllSalt(context)
            selectedOneSalt(context)
            selectedAllListPaths(context)
            selectedAllListFileNames(context)
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
     * @param context Contexto de la aplicacion
     */
    fun saveAllFilters(context: Context){
        viewModelScope.launch {
            SettingsDataStore.saveFilters(
                context = context,
                filtersConfigurations = stateFilters.copy()
            )
        }
    }

    /**
     * Metodo que filtra la lista de passwords por el texto que se le pase. Y aplica los filtros
     * configurados por el usario en la vista de "Filters"
     * @param searchText Texto que se le pasara para filtrar la lista
     */
    fun filterList(searchText: String){
        viewModelScope.launch {
            val list = state.listPaswordInformation
            //PasswordsInformations
            lateinit var filteredList: List<StatePaswordInformation>
            var listPaswordInformation: List<PasswordsInformations> = passwordInformationDao.getAllPasswordsInformations()
            filteredList = listPaswordInformation.map {
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
            }.filter {
                if(searchText.isBlank() ){
                    true
                } else if(stateFilters.title) {
                    it.password_title.contains(searchText, ignoreCase = true)
                } else if(stateFilters.user) {
                    it.username.contains(searchText, ignoreCase = true)
                } else if(stateFilters.notes) {
                    it.notes?.contains(searchText, ignoreCase = true) ?: false
                } else if(stateFilters.url) {
                    it.url?.contains(searchText, ignoreCase = true) ?: false
                } else if(stateFilters.email) {
                    it.email?.contains(searchText, ignoreCase = true) ?: false
                } else if(stateFilters.phone) {
                    it.phone.toString().contains(searchText, ignoreCase = true)
                } else {
                    false
                }
            }
            state = state.copy(
                listPaswordInformation = filteredList
            )
        }
    }

    /**
     * Metodo que selecciona el salt actual de la aplicacion
     * @param context Contexto de la aplicacion
     */
    private fun selectedAllSalt(context: Context){
        viewModelScope.launch {
            if(SettingsDataStore.getAllHexadecimalSalt(context = context).isEmpty() ){
                SettingsDataStore.saveOneHexadecimalSalt(
                    context = context,
                    hexadecimalSalt = ByteArray(16).also { SecureRandom().nextBytes(it) }.joinToString("") { "%02x".format(it) }
                )
            }
            stateListSalt = SettingsDataStore.getAllHexadecimalSalt(context = context)

        }
    }

    /**
     * Metodo que selecciona el salt actual de la aplicacion
     * @param context Contexto de la aplicacion
     */
    private fun selectedOneSalt(context: Context){
        viewModelScope.launch {
            if(SettingsDataStore.getAllHexadecimalSalt(context = context).isEmpty() ){
                SettingsDataStore.saveOneHexadecimalSalt(
                    context = context,
                    hexadecimalSalt = ByteArray(16).also { SecureRandom().nextBytes(it) }.joinToString("") { "%02x".format(it) }
                )
            }
            stateSalt = SettingsDataStore.getAllHexadecimalSalt(context = context).first()

        }
    }

    /**
     * Metodo que guarda un salt
     * @param context Contexto de la aplicacion
     * @param salt Salt en hexadecimal
     */
    fun saveOneSalt(context: Context, salt: String){
        viewModelScope.launch {
            SettingsDataStore.saveOneHexadecimalSalt(context = context, hexadecimalSalt = salt)
            stateListSalt = SettingsDataStore.getAllHexadecimalSalt(context = context)
        }
    }

    /**
     * Metodo que cambia el salt actual de la aplicacion
     * @param salt nuevo salt en hexadecimal que se quiere agregar
     */
    fun changeSalt(salt: String){
        viewModelScope.launch {
            stateSalt = salt
        }
    }

    /**
     * Metodo que cambia la contraseña en claro de la base de datos
     * @param password Contraseña en claro que se quiere cambiar
     */
    fun changeClearPasswordDb(password: String){
        stateClearPasswordDb = password
    }

    /**
     * Metodo que inicializa la lista de los paths de las bases de datos
     * @param context Contexto de la aplicacion
     */
    fun selectedAllListPaths(context: Context){
        viewModelScope.launch {
            SettingsDataStore.getDatabasesPaths(context = context).collect {
                stateListPaths = it.toMutableList()
            }
        }
    }

    /**
     * Metodo que cambia el path seleccionado
     * @path Path seleccionado
     */
    fun changeSelectedPath(path: String){
        viewModelScope.launch {
            stateSelectedPath = path
        }
    }

    /**
     * Metodo que guarda el path de una base de datos
     * @context Contexto de la aplicacion
     * @path Path a guardar
     */
    fun saveOnePathDatabase(context: Context, path: String){
        viewModelScope.launch {
            SettingsDataStore.saveOneDatabasePath(
                context = context,
                databasePath = path
            )
        }
    }

    /**
     * Metodo que inicializa la lista de los nombres de los archivos sin extension
     */
    fun selectedAllListFileNames(context: Context) {
        viewModelScope.launch {
            SettingsDataStore.getDatabasesPaths(context = context).collect {
                stateListFilesNames = it.toMutableList().map { iterator ->
                    iterator.split(Constants.STR_SLASH).last().replace(".db", "") // Se obtiene solo el nombre de la base de datos
                }
            }
        }
    }

    /**
     * Metodo que cambia el nombre del archivo seleccionado
     * @param fileName Nombre del archivo seleccionado
     */
    fun changeSelectedFileName(fileName: String){
        viewModelScope.launch {
            stateSelectedFileName = fileName
        }
    }

    /**
     * Metodo que elimina la base de datos temporal que crea room en la que se trabaja, esto se hace
     * por seguridad para que no quede rastro de que se intento crear un archivo con passwords que no
     * se guardo, si no se guarda explicitamente se tiene que eliminar porque la hace de una base
     * de datos temporal
     */
    fun deleteTempDatabase(context: Context){
        /*val database = AppDatabase.resetDatabase(context = context)
        passwordInformationDao = database.passwordsInformationsDao()*/
        AppDatabase.deleteDatabase(context = context)
        if(!state.listPaswordInformation.isEmpty() ){
            state = state.copy(// Se limpia la lista de passwords porque se elimino la base de datos
                listPaswordInformation = emptyList(),
                ready = true
            )
        }
    }

    /**
     * Metodo que vuelve a abrir room con usa base de datos
     */
    fun conectNewDatabase(context: Context){
        val database = AppDatabase.getInstance(context = context)
        passwordInformationDao = database.passwordsInformationsDao()
        getAllPasswordsInformations()
    }

    /**
     * Metodo que guarda la base de datos encriptada con la base de datos en claro temporal
     */
    fun saveTempDatabaseEncrypted(password: String){
        viewModelScope.launch {
            stateClearPasswordDb = password
            val inputFile1 = File(Constants.PATH_TMP_DATABASE)
            val outputFile1 = File(stateSelectedPath + ".db")
            val inputFile2 = File(Constants.PATH_TMP_DATABASE + "-shm")
            val outputFile2 = File(stateSelectedPath + "-shm")
            val inputFile3 = File(Constants.PATH_TMP_DATABASE + "-wal")
            val outputFile3 = File(stateSelectedPath + "-wal")
            encryptDatabaseFile(
                inputDataBaseFile = inputFile1,
                outputDatabaseFile = outputFile1,
                secretKey = generateSecretKey(
                    password = stateClearPasswordDb,
                    salt = hexStringToByteArray(
                        hexString = stateSalt
                    )
                )
            )
            encryptDatabaseFile(
                inputDataBaseFile = inputFile2,
                outputDatabaseFile = outputFile2,
                secretKey = generateSecretKey(
                    password = stateClearPasswordDb,
                    salt = hexStringToByteArray(
                        hexString = stateSalt
                    )
                )
            )
            encryptDatabaseFile(
                inputDataBaseFile = inputFile3,
                outputDatabaseFile = outputFile3,
                secretKey = generateSecretKey(
                    password = stateClearPasswordDb,
                    salt = hexStringToByteArray(
                        hexString = stateSalt
                    )
                )
            )
            val fileNameDb = stateSelectedPath.split(Constants.STR_SLASH).last()
            val finalPath = stateSelectedPath.replace(fileNameDb, fileNameDb + Constants.SALT_FILE_NAME)
            generateFileSalt(
                path = finalPath,
                salt = stateSalt,
            )
        }
    }

    /**
     * Metodo que reemplaza la base de datos actual por la base de datos que eligio el usuario en el
     * login, este metodo desencripta los binarios y pone el archivo en la base temporal para que
     * lo pueda abrir room
     */
    fun replaceEncryptedDBForTmpDB(){
        viewModelScope.launch {
            val inputFile1 = File(stateSelectedPath + ".db")
            val outputFile1 = File(Constants.PATH_TMP_DATABASE)
            val inputFile2 = File(stateSelectedPath + "-shm")
            val outputFile2 = File(Constants.PATH_TMP_DATABASE + "-shm")
            val inputFile3 = File(stateSelectedPath + "-wal")
            val outputFile3 = File(Constants.PATH_TMP_DATABASE + "-wal")
            try {
                decryptDatabaseFile(
                    inputDataBaseFile = inputFile1,
                    outputDatabaseFile = outputFile1,
                    secretKey = generateSecretKey(
                        password = stateClearPasswordDb,
                        salt = hexStringToByteArray(
                            hexString = stateSalt
                        )
                    )
                )
                decryptDatabaseFile(
                    inputDataBaseFile = inputFile2,
                    outputDatabaseFile = outputFile2,
                    secretKey = generateSecretKey(
                        password = stateClearPasswordDb,
                        salt = hexStringToByteArray(
                            hexString = stateSalt
                        )
                    )
                )
                decryptDatabaseFile(
                    inputDataBaseFile = inputFile3,
                    outputDatabaseFile = outputFile3,
                    secretKey = generateSecretKey(
                        password = stateClearPasswordDb,
                        salt = hexStringToByteArray(
                            hexString = stateSalt
                        )
                    )
                )
                stateLockMain = false
            } catch (exception: Exception){
                _errorDecryptUi.send(true)
                stateLockMain = true
            }
        }
    }
}