package com.ldt.BeatHive.common

import com.ldt.BeatHive.model.Media
import com.ldt.BeatHive.model.Playlist
import com.ldt.BeatHive.model.Song
import com.ldt.BeatHive.model.mp.ArtworkInfo
import java.util.*

/**
 * Manage every image cover showing on screen, such as playlist cover, song cover etc
 */
object ArtworkManager {
    val mapMediaIdToArtworkInfo = Collections.synchronizedMap(hashMapOf<String, ArtworkInfo>())
    @JvmStatic
    fun getMediaId(media: Media): String {
        return when(media) {
            is Song -> "song_${media.id}"
            is Playlist -> "playlist_${media.id}"
            else -> ""
        }
    }

    @JvmStatic
    fun getArtworkInfo(media: Media): ArtworkInfo? {
        val id = getMediaId(media)
        return mapMediaIdToArtworkInfo[id] ?: createArtworkInfo(media, id)?.also { mapMediaIdToArtworkInfo[id] = it}
    }

    private fun createArtworkInfo(media: Media, mediaId: String = getMediaId(media)): ArtworkInfo? {
        // TODO:
        // 1. Generate the artwork if needed
        // 2. Save into disk and get path
        // 3. Save artwork info to database
        // 4. return the Artwork info
        return null
    }
}