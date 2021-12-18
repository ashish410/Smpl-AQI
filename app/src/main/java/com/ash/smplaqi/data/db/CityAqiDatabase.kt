package com.ash.smplaqi.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ash.smplaqi.data.model.CityAqi

/**
 * database which is used to store the incoming data
 */
@Database(
    entities = [CityAqi::class],
    version = 1,
    exportSchema = false
)
abstract class CityAqiDatabase : RoomDatabase() {
    abstract fun cityAqiDao(): CityAqiDao

    companion object {
        @Volatile
        private var cityAqiDbInstance: CityAqiDatabase? = null

        fun createDb(context: Context): CityAqiDatabase =
            cityAqiDbInstance ?: synchronized(this) {
                cityAqiDbInstance ?: buildDb(context).also {
                    cityAqiDbInstance = it
                }
            }

        private fun buildDb(context: Context) =
            Room.databaseBuilder(context, CityAqiDatabase::class.java, "CityAqi.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}