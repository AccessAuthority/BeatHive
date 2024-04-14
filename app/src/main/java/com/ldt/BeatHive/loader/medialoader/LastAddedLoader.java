package com.ldt.BeatHive.loader.medialoader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.ldt.BeatHive.model.Song;
import com.ldt.BeatHive.util.PreferenceUtil;

import java.util.ArrayList;

public class LastAddedLoader {

    @NonNull
    public static ArrayList<Song> getLastAddedSongs(@NonNull Context context) {
        return SongLoader.getSongs(makeLastAddedCursor(context, null));
    }

    @NonNull
    public static ArrayList<Song> getLastAddedSongs(@NonNull Context context, @Nullable String _sortOrder) {
        return SongLoader.getSongs(makeLastAddedCursor(context, _sortOrder));
    }

    public static Cursor makeLastAddedCursor(@NonNull final Context context, @Nullable String _sortOrder) {
        long cutoff = PreferenceUtil.getInstance(context).getLastAddedCutoffTimeSecs();

        String sortOrder = _sortOrder == null ? MediaStore.Audio.Media.DATE_ADDED + " DESC" : _sortOrder;

        return SongLoader.makeSongCursor(
                context,
                MediaStore.Audio.Media.DATE_ADDED + ">?",
                new String[]{String.valueOf(cutoff)},
                sortOrder);
    }
}
