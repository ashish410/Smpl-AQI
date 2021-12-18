package com.ash.smplaqi.di

import android.content.Context
import com.ash.smplaqi.WS_URL
import com.ash.smplaqi.data.db.CityAqiDao
import com.ash.smplaqi.data.db.CityAqiDatabase
import com.ash.smplaqi.repository.AQIRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesOkhttpClient() = OkHttpClient()

    @Provides
    @Singleton
    fun providesOkHttpRequest(): Request {
        return Request.Builder().url(WS_URL).build()
    }

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext appContext: Context) =
        CityAqiDatabase.createDb(appContext)

    @Singleton
    @Provides
    fun providesAppDao(db: CityAqiDatabase) = db.cityAqiDao()
}