package xyz.kewiany.menusy.entity

data class Menu(
    val id: String,
    val name: String,
    val categories: List<Category>? = null
)