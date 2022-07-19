import xyz.kewiany.menusy.domain.model.Product
import kotlin.random.Random

//package xyz.kewiany.menusy
//
//import xyz.kewiany.menusy.domain.model.Category
//import xyz.kewiany.menusy.domain.model.Menu
//import xyz.kewiany.menusy.domain.model.Product
//
//fun createCategory(
//    id: String = "id",
//    name: String = "name"
//) = Category(
//    id = id,
//    name = name
//)
//
//fun createMenu(
//    id: String = "id",
//    name: String = "name",
//    categories: List<Category> = listOf(
//        createCategory(),
//        createCategory()
//    )
//) = Menu(
//    id = id,
//    name = name,
//    categories = categories
//)
//

fun createText(): String = Random.nextLong().toString()

fun createProduct(
    id: String = "id",
    name: String = "name",
    description: String = "description",
    price: Float = 0f,
    menuId: String = "menuId",
    categoryId: String = "categoryId",
) = Product(
    id = id,
    name = name,
    description = description,
    price = price,
    menuId = menuId,
    categoryId = categoryId,
)