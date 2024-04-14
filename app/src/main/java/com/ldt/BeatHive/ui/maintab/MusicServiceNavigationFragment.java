package com.ldt.BeatHive.ui.maintab;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.ldt.BeatHive.service.MusicServiceEventListener;
import com.ldt.BeatHive.ui.MusicServiceActivity;
import com.ldt.BeatHive.ui.widget.fragmentnavigationcontroller.NavigationFragment;
import com.ldt.BeatHive.ui.widget.fragmentnavigationcontroller.PresentStyle;

public abstract class MusicServiceNavigationFragment extends NavigationFragment implements MusicServiceEventListener {
    @Override
    public void onServiceConnected() {

    }

    @Override
    public void onPaletteChanged() {

    }

    @Override
    public void onServiceDisconnected() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if(activity instanceof MusicServiceActivity) ((MusicServiceActivity)activity).addMusicServiceEventListener(this);
    }

    @Override
    public void onDestroyView() {
        Activity activity = getActivity();
        if(activity instanceof MusicServiceActivity)((MusicServiceActivity)activity).removeMusicServiceEventListener(this);
        super.onDestroyView();
    }

    @Override
    public void onPlayStateChanged() {

    }

    @Override
    public void onRepeatModeChanged() {

    }

    @Override
    public void onShuffleModeChanged() {

    }

    @Override
    public void onQueueChanged() {

    }

    @Override
    public void onPlayingMetaChanged() {

    }

    @Override
    public void onMediaStoreChanged() {

    }

    @Override
    public int getPresentTransition() {
        return PresentStyle.FADE;
    }
}
