package xyz.kewiany.menusy.data

import xyz.kewiany.menusy.data.datastore.PlaceDataStore
import xyz.kewiany.menusy.data.network.api.PlaceApi
import xyz.kewiany.menusy.domain.repository.PlaceRepository
import xyz.kewiany.menusy.model.Place
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeApi: PlaceApi,
    private val placeDataStore: PlaceDataStore,
) : PlaceRepository {

    override suspend fun getPlace(id: String): Place {
        val response = placeApi.getPlace(id)
        val place = response?.place; checkNotNull(place)
        placeDataStore.savePlace(place)
        return place
    }
}