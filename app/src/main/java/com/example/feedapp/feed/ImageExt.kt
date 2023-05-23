package com.example.feedapp.feed

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat

fun getBitmapFromImage(context: Context, drawable: Int): Bitmap? {

    val db = ContextCompat.getDrawable(context, drawable)

    val bit = db?.intrinsicWidth?.let {
        Bitmap.createBitmap(
            it, db.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
    }

    val canvas = bit?.let { Canvas(it) }
    db?.setBounds(0, 0, canvas?.width ?: 0, canvas?.height ?: 0)
    if (canvas != null) {
        db?.draw(canvas)
    }
    return bit
}