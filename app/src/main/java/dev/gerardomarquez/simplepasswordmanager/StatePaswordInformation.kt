package dev.gerardomarquez.simplepasswordmanager

/**
 * Clase que representara el estado de la vista Main para realizar el patron arquitectonico ViewModel
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