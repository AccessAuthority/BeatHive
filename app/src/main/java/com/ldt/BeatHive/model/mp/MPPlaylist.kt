package com.ldt.BeatHive.model.mp

import com.ldt.BeatHive.model.Playlist

class MPPlaylist(id: Int, name: String): Playlist(id, name) {
    val songs = mutableListOf<Int>()
}