package com.example.feedapp.feed.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feedapp.feed.data.api.model.ArticlesItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFeedResponse(feedResponse: List<ArticlesItem>?)

    @Query("SELECT * FROM feed_table WHERE name = :sourceName")
    fun getArticlesBySourceName(sourceName: String): Flow<List<ArticlesItem>?>

    @Query("UPDATE feed_table Set isLiked=:isLike where articlesId=:id")
    fun toggleLikeUnLike(id: Int, isLike: Boolean)

}
