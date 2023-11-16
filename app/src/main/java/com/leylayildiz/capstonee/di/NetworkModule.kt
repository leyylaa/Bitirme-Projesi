package com.leylayildiz.capstonee.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.leylayildiz.capstonee.MainApplication
import com.leylayildiz.capstonee.common.Constants.BASE_URL
import com.leylayildiz.capstonee.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
     @Singleton
     @Provides
     fun provideChuckerInterceptor(@ApplicationContext  context : Context) = ChuckerInterceptor.Builder(context).build()

    @Singleton
    @Provides
    fun provideOkHttp(chucker : ChuckerInterceptor) = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("store","CoffeShop")
                return@Interceptor chain.proceed(builder.build())

            }
        )
        addInterceptor(chucker)
    }.build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient :OkHttpClient) = Retrofit.Builder().apply {
        addConverterFactory(GsonConverterFactory.create())
        baseUrl(BASE_URL)
        client(okHttpClient)
    }.build()

    @Singleton
    @Provides

    fun provideProductService(retrofit : Retrofit) = retrofit.create(ProductService::class.java)

    }
