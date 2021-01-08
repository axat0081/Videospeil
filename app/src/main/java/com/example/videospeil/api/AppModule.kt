package com.example.videospeil.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
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
    fun providesApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun providesDataRef(): DatabaseReference = FirebaseDatabase.getInstance().reference

    @Provides
    @Singleton
    fun providesUserIId(): String = FirebaseAuth.getInstance().currentUser!!.uid.toString()

    @Provides
    @Singleton
    fun providesFirebaseStorageRef(): StorageReference =
        FirebaseStorage.getInstance().getReference("Videos")
}
