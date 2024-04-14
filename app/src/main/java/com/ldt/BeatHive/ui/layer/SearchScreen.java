package com.ldt.BeatHive.ui.layer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ldt.BeatHive.R;
import com.ldt.BeatHive.service.MusicServiceEventListener;
import com.ldt.BeatHive.ui.maintab.CardLayerFragment;

/**
 * Search screen allows user to search songs, playlists, artists
 */
public class SearchScreen extends CardLayerFragment implements MusicServiceEventListener {
    private static final String TAG = "SearchScreen";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.screen_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public int getLayerMinHeight(Context context, int maxHeight) {
        return 0;
    }

    @Override
    public String getCardLayerTag() {
        return TAG;
    }
}
