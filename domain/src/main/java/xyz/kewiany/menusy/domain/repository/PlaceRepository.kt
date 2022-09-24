package xyz.kewiany.menusy.domain.repository

import xyz.kewiany.menusy.model.Place

interface PlaceRepository {
    suspend fun getPlace(id: String): Place
}