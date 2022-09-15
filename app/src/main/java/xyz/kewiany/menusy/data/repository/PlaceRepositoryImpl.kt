package xyz.kewiany.menusy.data.repository

import xyz.kewiany.menusy.data.source.local.PlaceDataStore
import xyz.kewiany.menusy.data.source.remote.api.PlaceApi
import xyz.kewiany.menusy.domain.model.Place
import xyz.kewiany.menusy.domain.repository.PlaceRepository
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