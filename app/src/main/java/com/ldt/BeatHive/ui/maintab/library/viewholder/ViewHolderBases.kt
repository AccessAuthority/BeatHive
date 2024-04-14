package com.ldt.BeatHive.ui.maintab.library.viewholder

import com.ldt.BeatHive.helper.songpreview.PreviewSong

interface BindTheme {
    fun bindTheme()
}

interface BindPreviewState {
    fun bindPreviewState(previewSong: PreviewSong?)
}

interface BindPlayingState {
    fun bindPlayingState()
}