package com.ldt.BeatHive.ui.maintab.library.genre;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ldt.BeatHive.loader.medialoader.GenreLoader;
import com.ldt.BeatHive.model.Genre;
import com.ldt.BeatHive.model.Song;
import com.ldt.BeatHive.ui.widget.bubblepicker.model.PickerItem;
import com.ldt.BeatHive.ui.widget.bubblepicker.rendering.PickerAdapter;
import com.ldt.BeatHive.util.MusicUtil;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class GenrePickerAdapter extends PickerAdapter<Genre> {

    public GenrePickerAdapter(Context context) {
        super(context);
    }

    @Override
    public boolean onBindItem(PickerItem item, boolean create, int i) {
        super.onBindItem(item,create,i);
        Genre genre = mData.get(i);
        item.setTitle(genre.name);
        item.setRadiusUnit(genre.songCount);
        // Glide
        ArrayList<Song> songs = GenreLoader.getSongs(mContext,genre.id);
        Glide.with(mContext).load(MusicUtil.getMediaStoreAlbumCoverUri(songs.get(0).albumId)).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                item.setBackgroundImage(resource);
                GenrePickerAdapter.this.notifyBackImageUpdated(i);
            }
        });

        return true;
    }
}
