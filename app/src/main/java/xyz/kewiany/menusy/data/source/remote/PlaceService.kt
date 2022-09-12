package xyz.kewiany.menusy.data.source.remote

import xyz.kewiany.menusy.data.source.remote.api.PlaceApi
import xyz.kewiany.menusy.data.source.remote.response.PlaceResponse
import xyz.kewiany.menusy.domain.model.Place
import javax.inject.Inject

class PlaceService @Inject constructor() : PlaceApi {

    override suspend fun getPlace(id: String): PlaceResponse? {
        return try {
            val data = Place(
                "id1",
                "name1",
                "address1"
            )
            PlaceResponse(data)
        } catch (e: Exception) {
            throw e
        }
    }
}