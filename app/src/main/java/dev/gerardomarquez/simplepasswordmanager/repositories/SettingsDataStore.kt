package dev.gerardomarquez.simplepasswordmanager.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// Extensión para crear DataStore
val Context.dataStore by preferencesDataStore(name = Constants.SETTINGS_FILE_NAME)

object SettingsDataStore {
    private val LIST_DATABASES_KEY = stringPreferencesKey(name = Constants.SETTINGS_LIST_DATABASES)
    private val FILTERS_KEY_TITLE = stringPreferencesKey(name = Constants.SETTINGS_FILTERS_TITLE)
    private val FILTERS_KEY_USER = stringPreferencesKey(name = Constants.SETTINGS_FILTERS_USER)
    private val FILTERS_KEY_NOTES = stringPreferencesKey(name = Constants.SETTINGS_FILTERS_NOTES)
    private val FILTERS_KEY_URL = stringPreferencesKey(name = Constants.SETTINGS_FILTERS_URL)
    private val FILTERS_KEY_EMAIL = stringPreferencesKey(name = Constants.SETTINGS_FILTERS_EMAIL)
    private val FILTERS_KEY_NUMBER = stringPreferencesKey(name = Constants.SETTINGS_FILTERS_NUMBER)
    private val LIST_SALT_KEY = stringPreferencesKey(name = Constants.SETTINGS_LIST_SALT)

    /**
     * Guarda una ruta de una base de datos, esta ruta debe ser la ultima que se creo
     * @param context Contexto de la aplicación
     * @param databasePath Ruta de la base de datos
     */
    suspend fun saveOneDatabasePath(context: Context, databasePath: String) {
        var listDatabasesPaths = getDatabasesPaths(context).toMutableList()
        if(listDatabasesPaths.contains(databasePath) )
            listDatabasesPaths.remove(databasePath)
        listDatabasesPaths.add(Constants.GLOBAL_START_INDEX, databasePath)
        saveDatabasesPaths(context, listDatabasesPaths)
    }

    /**
     * Guarda la lista de rutas de las bases de datos (reemplazandolas), la primera es la ultima en
     * ser agregada
     * @param context Contexto de la aplicación
     * @param listDatabasesPaths Lista de todas las rutas de las bases de datos
     */
    suspend fun saveDatabasesPaths(context: Context, listDatabasesPaths: List<String>) {
        val jsonList = Json.encodeToString(listDatabasesPaths)
        context.dataStore.edit { settings ->
            settings[LIST_DATABASES_KEY] = jsonList
        }
    }

    /**
     * Obtiene la lista de rutas de las bases de datos
     * @param context Contexto de la aplicación
     * @return Lista de rutas de las bases de datos
     */
    suspend fun getDatabasesPaths(context: Context): List<String> {
        val flowDatabasesPaths = context.dataStore.data.map { settings ->
            settings[LIST_DATABASES_KEY] ?: Constants.GLOBAL_STR_LEFT_BRACKET + Constants.GLOBAL_STR_RIGHT_BRACKET
        }
        val jsonString: String = flowDatabasesPaths.first()
        return Json.decodeFromString(jsonString)
    }

    /**
     * Guarda los filtros configurados por el usuario para utlizar la aplicacion
     * @param context Contexto de la aplicación
     * @param filtersConfigurations Filtros configurados por el usuario
     */
    suspend fun saveFilters(
        context: Context,
        filtersConfigurations: AllFilters
    ) {
        context.dataStore.edit {
            settings ->
            settings[FILTERS_KEY_TITLE] = filtersConfigurations.title.toString()
            settings[FILTERS_KEY_USER] = filtersConfigurations.user.toString()
            settings[FILTERS_KEY_NOTES] = filtersConfigurations.notes.toString()
            settings[FILTERS_KEY_URL] = filtersConfigurations.url.toString()
            settings[FILTERS_KEY_EMAIL] = filtersConfigurations.email.toString()
            settings[FILTERS_KEY_NUMBER] = filtersConfigurations.phone.toString()
        }
    }

    /**
     * Obtiene todos los filtros configurados por el usuario para utlizar la aplicacion
     * @param context Contexto de la aplicación
     * @return todos los filtros configurados por el usuario
     */
    suspend fun getFilters(
        context: Context
    ): AllFilters {
        val flowAllFilters = context.dataStore.data.map {
            settings ->
            AllFilters(
                title = settings[FILTERS_KEY_TITLE]?.toBoolean() ?: false,
                user = settings[FILTERS_KEY_USER]?.toBoolean() ?: false,
                notes = settings[FILTERS_KEY_NOTES]?.toBoolean() ?: false,
                url = settings[FILTERS_KEY_URL]?.toBoolean() ?: false,
                email = settings[FILTERS_KEY_EMAIL]?.toBoolean() ?: false,
                phone = settings[FILTERS_KEY_NUMBER]?.toBoolean() ?: false
            )
        }
        return flowAllFilters.first()
    }

    /**
     * Guarda una SALT para derivar el password igresado por el usuario y que dicho password sea valido
     * @param context Contexto de la aplicación
     * @param hexadecimalSalt Salt en exadecimal para que despues se convirta en un byteArray y que
     * lo pueda utilizar la libreria de derivacion argon2
     */
    suspend fun saveOneHexadecimalSalt(context: Context, hexadecimalSalt: String) {
        var listDatabasesPaths = getAllHexadecimalSalt(context).toMutableList()
        if(listDatabasesPaths.contains(hexadecimalSalt) )
            listDatabasesPaths.remove(hexadecimalSalt)
        listDatabasesPaths.add(Constants.GLOBAL_START_INDEX, hexadecimalSalt)
        saveAllHexadecimalSalt(context, listDatabasesPaths)
    }

    /**
     * Guarda la lista de SALT's generados por el usuario, la primera es la ultima en
     * ser agregada
     * @param context Contexto de la aplicación
     * @param listHexadecimalSalt Lista de todos los salts generados por el usuario
     */
    suspend fun saveAllHexadecimalSalt(context: Context, listHexadecimalSalt: List<String>) {
        val jsonList = Json.encodeToString(listHexadecimalSalt)
        context.dataStore.edit { settings ->
            settings[LIST_SALT_KEY] = jsonList
        }
    }

    /**
     * Consulta todos los salts generados por el usuario
     * @param context Contexto de la aplicación
     * @return Lista de todos los salts generados por el usuario
     */
    suspend fun getAllHexadecimalSalt(context: Context): List<String> {
        val flowDatabasesPaths = context.dataStore.data.map { settings ->
            settings[LIST_SALT_KEY] ?: Constants.GLOBAL_STR_LEFT_BRACKET + Constants.GLOBAL_STR_RIGHT_BRACKET
        }
        val jsonString: String = flowDatabasesPaths.first()
        return Json.decodeFromString(jsonString)
    }
}
