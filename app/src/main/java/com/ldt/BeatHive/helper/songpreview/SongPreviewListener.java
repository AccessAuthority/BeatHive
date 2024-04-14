package com.ldt.BeatHive.helper.songpreview;

public interface SongPreviewListener {
        void onSongPreviewStart(PreviewSong song);
        void onSongPreviewFinish(PreviewSong song);
    }