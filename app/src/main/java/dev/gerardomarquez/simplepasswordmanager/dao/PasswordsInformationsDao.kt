package dev.gerardomarquez.simplepasswordmanager.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.gerardomarquez.simplepasswordmanager.entities.PasswordsInformations
import dev.gerardomarquez.simplepasswordmanager.utils.Constants

/**
 * Interfaz que contiene los metodos para interactuar con la tabla "passwords_informations" en la base de datos
 */
@Dao
interface PasswordsInformationsDao {
    /**
     * Metodo que inserta un objeto de tipo PasswordsInformations en la tabla "passwords_informations"
     * @param passwordInformation Objeto de tipo PasswordsInformations que se quiere insertar
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnePasswordInformation(passwordInformation: PasswordsInformations)

    /**
     * Metodo que obtiene todos los objetos de tipo PasswordsInformations de la tabla "passwords_informations"
     * @return Lista de objetos de tipo PasswordsInformations (todos los renglones de la tabla)
     */
    @Query(value = Constants.QUERY_SELECT_ALL)
    suspend fun getAllPasswordsInformations(): List<PasswordsInformations>

    /**
     * Metodo que obtiene un objeto de tipo PasswordsInformations de la tabla "passwords_informations" por su id del objeto
     * @param Objeto de tipo PasswordsInformations que se quiere obtener (solo se tomara el id de dicho objeto)
     */
    @Delete
    suspend fun deletePasswordInformation(passwordInformation: PasswordsInformations)
}