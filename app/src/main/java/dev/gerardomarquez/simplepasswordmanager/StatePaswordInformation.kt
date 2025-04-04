package dev.gerardomarquez.simplepasswordmanager

import dev.gerardomarquez.simplepasswordmanager.entities.PasswordsInformations

/**
 * Clase que representara el estado de la vista Main para realizar el patron arquitectonico ViewModel,
 * solo par la informacion de un elemento
 */
data class StatePaswordInformation (
    val id: Int,
    val password_title: String,
    val username: String,
    val password: String,
    val token: String?,
    val email: String?,
    val phone: Long?,
    val url: String?,
    val notes: String?
)

/**
 * Clase que contiene la lista de toda la informacion de un password
 */
data class ListStatePaswordInformation (
    val listPaswordInformation: List<StatePaswordInformation> = listOf(),
    val selectedPassword: PasswordsInformations = PasswordsInformations( // Si se selecciono alguno de la lista
        id = 0,
        password_title = "",
        username = "",
        password = "",
        token = null,
        email = null,
        phone = null,
        url = null,
        notes = null
    ),
    val ready: Boolean = false // Para indicar si la lista esta cargada por completo o no
)