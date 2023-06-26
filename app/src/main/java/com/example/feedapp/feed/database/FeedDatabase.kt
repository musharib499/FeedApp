package com.example.feedapp.feed.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.feedapp.feed.data.api.model.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1)
@TypeConverters(MapTypeConverter::class)
abstract class FeedDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao

    companion object {
        @Volatile
        private var INSTANCE: FeedDatabase? = null

        fun getInstance(context: Context): FeedDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FeedDatabase::class.java,
                    "feed_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}