package com.ldt.BeatHive.ui.maintab.subpages.viewplaylist;

import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.ldt.BeatHive.App;
import com.ldt.BeatHive.R;
import com.ldt.BeatHive.helper.AppExecutors;
import com.ldt.BeatHive.helper.Reliable;
import com.ldt.BeatHive.helper.ReliableEvent;
import com.ldt.BeatHive.model.Playlist;
import com.ldt.BeatHive.model.Song;
import com.ldt.BeatHive.ui.base.MPViewModel;
import com.ldt.BeatHive.ui.bottomsheet.SortOrderBottomSheet;
import com.ldt.BeatHive.util.PlaylistArtworkGenerator;
import com.ldt.BeatHive.util.MusicUtil;
import com.ldt.BeatHive.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ViewPlaylistViewModel extends MPViewModel {
    public static class State {
        public Playlist mPlaylist;
        public String mTitle = "";
        public String mDescription = "";
        public final List<Song> songs = new ArrayList<>();
        public Bitmap mCoverImage = null;
        public int sortOrder = 0;
    }

    public MutableLiveData<ReliableEvent<State>> getStateLiveData() {
        return mStateLiveData;
    }

    private final MutableLiveData<ReliableEvent<State>> mStateLiveData = new MutableLiveData<>();

    public void refreshData() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            ReliableEvent<State> event = mStateLiveData.getValue();
            final State state = event != null ? event.getReliable().getData() : null;

            Reliable<?> reliable = null;

            if (state == null || state.mPlaylist == null) {
                reliable = Reliable.failed(MESSAGE_CODE_INVALID_PARAMS, new IllegalArgumentException("Require playlist parameter"));
            }

            int order = 0;
            if (reliable == null) {
                if (Util.equals(ACTION_SET_PARAMS, event.getAction()) && Util.equals(state.mPlaylist.name, App.getInstance().getApplicationContext().getResources().getString(R.string.playlist_last_added))) {
                    order = 2;
                } else {
                    order = App.getInstance().getPreferencesUtility().getSharePreferences().getInt("sort_order_playlist_" + state.mPlaylist.name + "_" + state.mPlaylist.id, 0);
                }
            }

            List<Song> songs = null;
            if (reliable == null) {
                songs = MusicUtil.getPlaylistSongList(App.getInstance().getApplicationContext(), state.mPlaylist, SortOrderBottomSheet.mSortOrderCodes[order]);
                if (songs == null) {
                    reliable = Reliable.failed(MESSAGE_CODE_INVALID_RESPONSE, new NullPointerException("Null song playlist"));
                }
            }

            String title = "", description = "";
            if (reliable == null) {
                title = state.mPlaylist.name;
                ArrayList<String> artists = new ArrayList<>();
                for (int i = 0; i < songs.size() && artists.size() < 5; i++) {
                    Song song = songs.get(i);
                    if (!artists.contains(song.artistName)) artists.add(song.artistName);
                }
                description = TextUtils.join(" · ", artists);
            }

            // should we sets result here ?
            if (reliable == null) {
                ReliableEvent<State> latestEvent = mStateLiveData.getValue();
                final State latestState = latestEvent != null ? latestEvent.getReliable().getData() : null;

                if (latestState != null) {
                    latestState.mTitle = title;
                    latestState.mDescription = description;
                    latestState.songs.clear();
                    latestState.songs.addAll(songs);
                    latestState.sortOrder = order;
                    mStateLiveData.postValue(new ReliableEvent<>(Reliable.success(latestState), ACTION_RELOAD_DATA));
                }
            }

            // now load the cover bitmap
            Bitmap coverBitmap = null;
            if (reliable == null) {
                try {
                    coverBitmap = PlaylistArtworkGenerator.getBitmap(App.getInstance().getApplicationContext(), songs, false, false);
                    if (coverBitmap == null) {
                        reliable = Reliable.failed(MESSAGE_CODE_INVALID_RESPONSE, new NullPointerException("Cover bitmap is null"));
                    }
                } catch (Exception e) {
                    reliable = Reliable.failed(MESSAGE_CODE_INVALID_RESPONSE, e);
                }
            }

            // now post the bitmap result
            ReliableEvent<State> latestEvent = mStateLiveData.getValue();
            final State latestState = latestEvent != null ? latestEvent.getReliable().getData() : null;
            if (reliable != null) {
                Reliable<State> failedReliable = Reliable.custom(Reliable.Type.FAILED, latestState, reliable.mMessageCode, reliable.getMessage(), reliable.mThrowable);
                mStateLiveData.postValue(new ReliableEvent<State>(failedReliable, ACTION_RELOAD_DATA));
            } else {
                if (latestState != null) {
                    latestState.mTitle = title;
                    latestState.mDescription = description;
                    latestState.songs.clear();
                    latestState.songs.addAll(songs);
                    latestState.mCoverImage = coverBitmap;
                }
                Reliable<State> successReliable = Reliable.success(latestState);
                mStateLiveData.postValue(new ReliableEvent<>(successReliable, ACTION_RELOAD_DATA));

            }

        });
    }
}
