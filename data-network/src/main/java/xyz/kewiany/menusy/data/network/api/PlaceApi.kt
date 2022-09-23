package xyz.kewiany.menusy.data.network.api

import xyz.kewiany.menusy.data.network.response.PlaceResponse

interface PlaceApi {
    suspend fun getPlace(id: String): PlaceResponse?
}