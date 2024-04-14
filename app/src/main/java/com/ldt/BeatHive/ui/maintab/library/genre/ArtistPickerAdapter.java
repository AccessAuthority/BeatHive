package com.ldt.BeatHive.ui.maintab.library.genre;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ldt.BeatHive.glide.ArtistGlideRequest;
import com.ldt.BeatHive.glide.GlideApp;
import com.ldt.BeatHive.model.Artist;
import com.ldt.BeatHive.ui.widget.bubblepicker.model.PickerItem;
import com.ldt.BeatHive.ui.widget.bubblepicker.physics.PhysicsEngine;
import com.ldt.BeatHive.ui.widget.bubblepicker.rendering.PickerAdapter;

public class ArtistPickerAdapter extends PickerAdapter<Artist> {

    public ArtistPickerAdapter(Context context) {
        super(context);
    }

    @Override
    public boolean onBindItem(PickerItem item, boolean create, int i) {
        Artist artist = mData.get(i);
        item.setTitle(artist.getName());
        item.setRadiusUnit(PhysicsEngine.INSTANCE.interpolate(1,2f,((float) artist.getSongCount())/getItemCount()));
        // Glide
        ArtistGlideRequest.Builder.from(GlideApp.with(mContext), artist)
                // .tryToLoadOriginal(true)
                .generateBuilder(mContext)
                .buildRequestDrawable()
                .centerCrop()
               // .error(R.drawable.music_style)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        item.setBackgroundImage(resource);
                    }
                });

        return true;
    }

}
