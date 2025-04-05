package dev.gerardomarquez.simplepasswordmanager.repositories

data class AllFilters(
    val title: Boolean = true,
    val user: Boolean = false,
    val notes: Boolean = false,
    val url: Boolean = false,
    val email: Boolean = false,
    val phone: Boolean = false
)