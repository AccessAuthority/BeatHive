package com.ldt.BeatHive.loader.artwork

import android.graphics.Bitmap
import com.ldt.BeatHive.model.Media

interface ArtworkGenerator<T> where T : Media{
    fun get(media: T): Bitmap
}