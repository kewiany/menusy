package xyz.kewiany.menusy.data.repository

import xyz.kewiany.menusy.data.source.local.CacheDataStore
import xyz.kewiany.menusy.data.source.remote.api.PlaceApi
import xyz.kewiany.menusy.domain.model.Place
import xyz.kewiany.menusy.domain.repository.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeApi: PlaceApi,
    private val cacheDataStore: CacheDataStore,
) : PlaceRepository {

    override suspend fun getPlace(id: String): Place {
        val response = placeApi.getPlace(id)
        val data = response?.place; requireNotNull(data)
        cacheDataStore.savePlace(data)
        return data
    }
}