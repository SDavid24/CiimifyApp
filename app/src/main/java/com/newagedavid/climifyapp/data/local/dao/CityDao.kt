package com.newagedavid.climifyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.newagedavid.climifyapp.data.local.entity.City
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(city: City): Long

    @Query("SELECT * FROM city ORDER BY createdAt DESC")
    fun getAllCities(): Flow<List<City>>

    @Update
    suspend fun updateCity(city: City)

    @Delete
    suspend fun deleteCity(city: City)

    @Query("SELECT * FROM city WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): City?

    @Query("SELECT * FROM city")
    fun getFavoriteCities(): Flow<List<City>>

    // Returns the city together with ALL hourlies for that city — we'll pick the latest in the ViewModel or repository
    @Transaction
    @Query("SELECT * FROM city")
    fun getFavoritesWithHourlies(): Flow<List<City>>

}