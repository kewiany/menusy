package xyz.kewiany.menusy.data.network

import xyz.kewiany.menusy.data.network.api.PlaceApi
import xyz.kewiany.menusy.data.network.response.PlaceResponse
import xyz.kewiany.menusy.domain.model.Place
import javax.inject.Inject
import kotlin.random.Random

class PlaceService @Inject constructor() : PlaceApi {

    override suspend fun getPlace(id: String): PlaceResponse? {
        return try {
            val data = Place(
                Random.nextLong().toString(),
                Random.nextLong().toString(),
                Random.nextLong().toString()
            )
            PlaceResponse(data)
        } catch (e: Exception) {
            throw e
        }
    }
}