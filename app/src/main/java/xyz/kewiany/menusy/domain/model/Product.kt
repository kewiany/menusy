package xyz.kewiany.menusy.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Float,
    val menuId: String,
    val categoryId: String? = null
)