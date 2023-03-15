package com.dibanand.newsez.di

import android.content.Context
import androidx.room.Room
import com.dibanand.newsez.db.NewsItemDao
import com.dibanand.newsez.db.NewsItemDatabase
import com.dibanand.newsez.network.NewsApiService
import com.dibanand.newsez.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideDao(database: NewsItemDatabase): NewsItemDao {
        return database.getNewsItemDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsItemDatabase {
        return Room.databaseBuilder(
            context,
            NewsItemDatabase::class.java,
            "newsez_db.db"
        ).build()
    }

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().also {
            it.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}