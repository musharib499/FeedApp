package com.example.feedapp.feed.database

import androidx.room.TypeConverter
import com.example.feedapp.feed.data.api.model.ArticlesItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapTypeConverter {
    @TypeConverter
    fun listToJson(value: List<ArticlesItem>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<ArticlesItem>::class.java).toList()

}