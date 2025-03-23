package dev.gerardomarquez.simplepasswordmanager.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.gerardomarquez.simplepasswordmanager.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// Extensión para crear DataStore
val Context.dataStore by preferencesDataStore(name = Constants.SETTINGS_FILE_NAME)

object SettingsDataStore {
    private val LIST_DATABASES_KEY = stringPreferencesKey(name = Constants.SETTINGS_LIST_DATABASES)

    /**
     * Guarda una ruta de una base de datos, esta ruta debe ser la ultima que se creo
     * @param context Contexto de la aplicación
     * @param databasePath Ruta de la base de datos
     */
    suspend fun saveOneDatabasePath(context: Context, databasePath: String) {
        var listDatabasesPaths = getDatabasesPaths(context).toList().first()
        var aux = listDatabasesPaths.toMutableList()
        /*
        if(listDatabasesPaths.contains(databasePath) )
            listDatabasesPaths.remove(databasePath)
        listDatabasesPaths.add(Constants.GLOBAL_START_INDEX, databasePath)
        saveDatabasesPaths(context, listDatabasesPaths)*/
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
    fun getDatabasesPaths(context: Context): Flow<List<String>> {
        return context.dataStore.data.map { settings ->
            val jsonList = settings[LIST_DATABASES_KEY] ?: Constants.STR_CORCHETES
            Json.decodeFromString(jsonList)
        }
    }
}