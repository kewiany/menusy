package xyz.kewiany.menusy.domain.model

data class Menu(
    val id: String,
    val name: String,
    val categories: List<Category>? = null
)