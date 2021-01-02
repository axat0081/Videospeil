package com.example.videospeil.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit):Api =retrofit.create(Api::class.java)
}