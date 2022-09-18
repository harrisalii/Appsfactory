package com.appsfactory.test.di

import android.app.Application
import androidx.room.Room
import com.appsfactory.test.BuildConfig
import com.appsfactory.test.data.local.room.AlbumDao
import com.appsfactory.test.data.local.room.AlbumDatabase
import com.appsfactory.test.data.remote.LastFMApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        val okhttpBuilder = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor).addInterceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", BuildConfig.LAST_FM_API_KEY)
                    .addQueryParameter("format", "json")
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }
        }
        return okhttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("http://ws.audioscrobbler.com/2.0/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideLastFMApi(retrofit: Retrofit): LastFMApi = retrofit.create()

    @Singleton
    @Provides
    fun provideAlbumDatabase(app: Application): AlbumDatabase {
        return Room.databaseBuilder(app, AlbumDatabase::class.java, "album.db").build()
    }

    @Singleton
    @Provides
    fun provideAlbumDao(albumDatabase: AlbumDatabase): AlbumDao = albumDatabase.dao
}