package com.ldt.BeatHive.ui.maintab.subpages;

import com.ldt.BeatHive.addon.lastfm.rest.model.LastFmArtist;

import java.util.ArrayList;

public interface ResultCallback {
    void onSuccess(LastFmArtist lastFmArtist);
    void onFailure(Exception e);
    void onSuccess(ArrayList<String> mResult);
}