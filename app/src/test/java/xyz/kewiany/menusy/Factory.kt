package xyz.kewiany.menusy

import xyz.kewiany.menusy.entity.Category
import xyz.kewiany.menusy.entity.Menu
import xyz.kewiany.menusy.entity.Product

fun createCategory(
    id: String = "id",
    name: String = "name"
) = Category(
    id = id,
    name = name
)

fun createMenu(
    id: String = "id",
    name: String = "name",
    categories: List<Category> = listOf(
        createCategory(),
        createCategory()
    )
) = Menu(
    id = id,
    name = name,
    categories = categories
)

fun createProduct(
    id: String = "id",
    name: String = "name",
    description: String = "description",
    price: String = "price",
    menuId: String = "menuId",
    categoryId: String = "categoryId"
) = Product(
    id = id,
    name = name,
    description = description,
    price = price,
    menuId = menuId,
    categoryId = categoryId
)