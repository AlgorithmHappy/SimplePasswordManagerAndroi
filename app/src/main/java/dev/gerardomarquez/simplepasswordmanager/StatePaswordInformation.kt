package dev.gerardomarquez.simplepasswordmanager

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
    val ready: Boolean = false // Para indicar si la lista esta cargada por completo o no
)