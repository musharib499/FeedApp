package com.example.feedapp.feed.di

import com.example.feedapp.feed.data.api.FeedApi
import com.example.feedapp.feed.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object FeedRepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideFeedRepository(api: FeedApi, defaultDispatcher: CoroutineDispatcher) : FeedRepository {
        return FeedRepository(api,defaultDispatcher)
    }
}