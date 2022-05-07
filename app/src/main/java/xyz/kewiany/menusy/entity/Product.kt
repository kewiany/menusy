package xyz.kewiany.menusy.entity

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: String,
    val menuId: String,
    val categoryId: String? = null
)