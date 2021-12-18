package com.ash.smplaqi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ash.smplaqi.data.model.CityAqi
import kotlinx.coroutines.flow.Flow

@Dao
interface CityAqiDao {
    @Insert
    fun insertAll(dataList: List<CityAqi>)

    @Query("DELETE FROM CityAqi")
    fun deleteData()

    @Query("SELECT * FROM CityAqi ca WHERE ca.seconds = (SELECT MAX(seconds) FROM CityAqi a WHERE a.city = ca.city) GROUP BY ca.city")
    fun getLastValues(): Flow<List<CityAqi>>

    @Query("SELECT * FROM CityAqi baq WHERE baq.city = :city ORDER BY baq.seconds DESC limit 50")
    fun getLastValuesOf(city: String): Flow<List<CityAqi>>
}