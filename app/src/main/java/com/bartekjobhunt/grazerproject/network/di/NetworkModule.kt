package com.bartekjobhunt.grazerproject.network.di

import com.bartekjobhunt.grazerproject.network.RetrofitClient
import com.bartekjobhunt.grazerproject.network.service.GrazerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitClient.getClient()
    }

    @Provides
    @Singleton
    fun provideGrazerService(retrofit: Retrofit): GrazerService {
        return retrofit.create(GrazerService::class.java)
    }
}
