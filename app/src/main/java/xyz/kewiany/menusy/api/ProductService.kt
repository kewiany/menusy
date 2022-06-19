package xyz.kewiany.menusy.api

import xyz.kewiany.menusy.entity.Product
import javax.inject.Inject

class ProductService @Inject constructor() : ProductApi {

    override suspend fun getProducts(menuId: String): ProductsResponse? {
        return get(menuId, null)
    }

    override suspend fun getProducts(menuId: String, categoryId: String): ProductsResponse? {
        return get(menuId, categoryId)
    }

    private fun get(menuId: String?, categoryId: String?): ProductsResponse {
        return try {
            val data = if (menuId == "m0") {
                listOf(
                    Product(
                        id = "p0",
                        name = "tomato soup",
                        description = "italian tomatoes, basiland a touch of fennel",
                        price = "10.90",
                        menuId = "m0"
                    )
                )
            } else {
                when (categoryId) {
                    "c0" -> {
                        listOf(
                            Product(
                                id = "p0",
                                name = "chicken wings",
                                description = "crispy chicken wings, louisiana hot sauce, buttermilk blue cheese ranch dressing, carrot, celery",
                                price = "31.90",
                                menuId = "m1",
                                categoryId = "c0"
                            ),
                            Product(
                                id = "p1",
                                name = "salmon tatar",
                                description = "salmon, soy sauce, chives, cucumber, mustard, purple potato chips",
                                price = "36.90",
                                menuId = "m1",
                                categoryId = "c0"
                            ),
                        )
                    }
                    "c1" -> {
                        listOf(
                            Product(
                                id = "p2",
                                name = "apple cake",
                                description = "warm apple cake with shortcrust pastry, vanilla ice cream",
                                price = "22.90",
                                menuId = "m1",
                                categoryId = "c1"
                            )
                        )
                    }
                    "c2" -> {
                        listOf(
                            Product(
                                id = "p3",
                                name = "tea",
                                description = "black, earl grey, green, jasmine, chamomile, mint",
                                price = "14.00",
                                menuId = "m1",
                                categoryId = "c2"
                            ),
                            Product(
                                id = "p4",
                                name = "almond frappe",
                                description = "black or with milk",
                                price = "18.00",
                                menuId = "m1",
                                categoryId = "c2"
                            )
                        )
                    }
                    "c3" -> {
                        listOf(
                            Product(
                                id = "p5",
                                name = "toma juice",
                                description = "apple, orange, grapefruit, tomato",
                                price = "9.00",
                                menuId = "m1",
                                categoryId = "c3"
                            ),
                            Product(
                                id = "p6",
                                name = "cisowianka",
                                description = "still, sparkling",
                                price = "19.00",
                                menuId = "m1",
                                categoryId = "c3"
                            )
                        )
                    }
                    else -> emptyList()
                }
            }
            ProductsResponse(data)
        } catch (e: Exception) {
            throw e
        }
    }
}