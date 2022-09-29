package xyz.kewiany.menusy.data.database

interface OrderLocalDataSource {
    suspend fun add(
        date: String,
        totalQuantity: Int,
        totalPrice: Float,
        placeName: String,
        placeAddress: String
    ): Long
}