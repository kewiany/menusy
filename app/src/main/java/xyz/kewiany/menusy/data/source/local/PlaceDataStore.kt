package xyz.kewiany.menusy.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.kewiany.menusy.domain.model.Place
import javax.inject.Inject

interface PlaceDataStore {
    val place: Flow<Place>
    suspend fun savePlace(place: Place)
}

class PlaceDataStoreImpl @Inject constructor(
    private val gson: Gson,
    private val dataStore: DataStore<Preferences>
) : PlaceDataStore {

    override val place: Flow<Place> = dataStore.data.map { preferences ->
        val json = preferences[Keys.PLACE] ?: throw NullPointerException()
        gson.fromJson(json, Place::class.java)
    }

    override suspend fun savePlace(place: Place) {
        dataStore.edit { preferences -> preferences[Keys.PLACE] = gson.toJson(place) }
    }

    private object Keys {
        val PLACE = stringPreferencesKey("place")
    }
}