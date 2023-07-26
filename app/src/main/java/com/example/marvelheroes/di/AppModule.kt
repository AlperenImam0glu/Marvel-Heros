package com.example.marvelheroes.di


import com.example.marvelheroes.paging.network.RetrofitService
import com.example.marvelheroes.repository.MainRepository
import com.example.marvelheroes.util.Const.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  AppModule{

    @Singleton
    @Provides
    fun provideMainRepository(retrofitService: RetrofitService) : MainRepository {
        return MainRepository(retrofitService)
    }

    @Singleton
    @Provides
    fun provideRetrofit() : RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

}