package dev.gerardomarquez.simplepasswordmanager.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.gerardomarquez.simplepasswordmanager.utils.Constants

/**
 * Clase que representa la entidad de la tabla "passwords_informations" en la base de datos
 */
@Entity(
    tableName = "passwords_informations",
    indices = [Index(value = ["password_title"], unique = true)]
)
data class PasswordsInformations (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val password_title: String,
    val username: String,
    val password: String,
    val token: String?,
    val email: String?,
    val phone: Long?,
    val url: String?,
    val notes: String?
){
    /**
     * Constructor secundario que valida las restricciones de las columnas
     */
    init {
        require(password_title.length <= 20) { Constants.TXT_ERROR_PASSWORD_TITLE_CONSTRAINT }
        require(username.length <= 20) { Constants.TXT_ERROR_USERNAME_CONSTRAINT }
        require(password.length <= 18) { Constants.TXT_ERROR_PASSWORD_CONSTRAINT }
        if(token != null) require(token.length <= 257) { Constants.TXT_ERROR_TOKEN_CONSTRAINT }
        if(email != null) require(email.length <= 30) { Constants.TXT_ERROR_EMAIL_CONSTRAINT }
        if(phone != null) require(phone in 1..999_999_999_999) { Constants.TXT_ERROR_PHONE_CONSTRAINT }
        if(url != null) require(url.length <= 100) { Constants.TXT_ERROR_URL_CONSTRAINT }
        if(notes != null) require(notes.length <= 500) { Constants.TXT_ERROR_NOTES_CONSTRAINT }
    }
}