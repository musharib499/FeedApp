package com.example.feedapp.feed.di

import android.content.Context
import com.example.feedapp.feed.data.api.FeedApi
import com.example.feedapp.feed.database.FeedDao
import com.example.feedapp.feed.database.FeedDatabase
import com.example.feedapp.feed.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeedModule {
    @Provides
    @Singleton
    fun providerFeedDatabase (@ApplicationContext context: Context) : FeedDatabase = FeedDatabase.getInstance(context)

    @Provides
    @Singleton
    fun providerFeedDao(database: FeedDatabase): FeedDao = database.feedDao()

    @Provides
    @Singleton
    fun provideFeedRepository(api: FeedApi, feedDao: FeedDao,defaultDispatcher: CoroutineDispatcher) : FeedRepository {
        return FeedRepository(api,feedDao,defaultDispatcher)
    }
}