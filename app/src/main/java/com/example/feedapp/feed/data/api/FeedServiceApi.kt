package com.example.feedapp.feed.data.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


//@Module
//@InstallIn(SingletonComponent::class)
//object FeedServiceApi {
//
//    @Singleton
//    @Provides
//    fun provideFeedServiceApi(retrofit: Retrofit): FeedApi {
//        return retrofit.create(FeedApi::class.java)
//    }
//
//}