package com.example.feedapp.base.component.baseApi

import com.example.feedapp.feed.data.api.FeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceApi {

    private const val HOST = "https://newsapi.org/v2/"
    private const val API_KEY = "1c39fb98c11643d6bd33dc53d8a171ea"

    @Singleton
    @Provides
    fun provideBaseURL(): String {
        return HOST
    }

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideInterceptorWithKey(): Interceptor {
        return Interceptor {
            val originalRequest = it.request()
            val newHttpUrl = originalRequest.url.newBuilder()
                .addQueryParameter("apiKey", API_KEY)
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .build()
            it.proceed(newRequest)
        }
    }
    @Singleton
    @Provides
    fun provideOkHttp(interceptor: HttpLoggingInterceptor, interceptorWithKey: Interceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder();
        okHttpClient.callTimeout(60, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.readTimeout(60, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(interceptor)
        okHttpClient.addInterceptor(interceptorWithKey)
        return okHttpClient.build();
    }


    @Singleton
    @Provides
    fun provideRestAdapter(
        baseURL: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retro = Retrofit.Builder().baseUrl(baseURL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build();
        return retro;
    }

    @Singleton
    @Provides
    fun provideFeedServiceApi(retrofit: Retrofit): FeedApi {
        return retrofit.create(FeedApi::class.java)
    }

    @Provides
    fun provideIDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}