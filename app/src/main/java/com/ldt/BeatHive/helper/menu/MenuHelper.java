package com.ldt.BeatHive.helper.menu;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.ldt.BeatHive.App;
import com.ldt.BeatHive.R;
import com.ldt.BeatHive.helper.songpreview.SongPreviewController;
import com.ldt.BeatHive.model.Artist;
import com.ldt.BeatHive.model.Playlist;
import com.ldt.BeatHive.model.Song;
import com.ldt.BeatHive.service.MusicPlayerRemote;
import com.ldt.BeatHive.ui.MusicServiceActivity;
import com.ldt.BeatHive.ui.AppActivity;
import com.ldt.BeatHive.ui.dialog.AddToPlaylistDialog;
import com.ldt.BeatHive.ui.dialog.DeletePlaylistDialog;
import com.ldt.BeatHive.ui.dialog.RenamePlaylistDialog;
import com.ldt.BeatHive.ui.widget.WeakContextAsyncTask;
import com.ldt.BeatHive.util.MusicUtil;
import com.ldt.BeatHive.util.PlaylistsUtil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Karim Abou Zeid (kabouzeid)
 * modified by Le Dinh Trung (dtrung98)
 */

public class MenuHelper {
    @StringRes
    public static final int[] AUTO_GEN_PLAYLIST_OPTION = new int[]{
            R.string.play_next,
            R.string.play_preview,
            R.string.add_to_queue,
            R.string.add_playlist_to_playlist,
    };
    @StringRes
    public static final int[] USER_PLAYLIST_OPTION = new int[]{
            R.string.play_next,
            R.string.play_preview,
            R.string.add_to_queue,
            R.string.add_playlist_to_playlist,
            R.string.rename,
            R.string.divider,
            R.string.delete_from_playlist
    };

    @StringRes
    public static final int[] ARTIST_OPTION = new int[] {
            R.string.play,
            R.string.play_preview,
            R.string.play_next,
            R.string.add_to_queue,
            R.string.add_to_playlist
    };

    public static boolean handleMenuClick(@NonNull AppCompatActivity activity, @NonNull Artist artist, int string_res_option) {
        switch (string_res_option) {
            case R.string.play:
                MusicPlayerRemote.openAndShuffleQueue(artist.getSongs(),true);
                return true;
            case R.string.play_next:
                MusicPlayerRemote.playNext(artist.getSongs());
                return true;
            case R.string.play_preview:
                if(activity instanceof MusicServiceActivity) {
                    SongPreviewController preview = ((AppActivity) activity).getSongPreviewController();
                    if (preview != null) {
                        if (preview.isPlayingPreview())
                            preview.cancelPreview();
                        else {

                            ArrayList<Song> list = new ArrayList<>(artist.getSongs());
                            Collections.shuffle(list);
                            preview.previewSongs(list);
                        }
                    }
                }
                return true;
            case R.string.add_to_queue:
                MusicPlayerRemote.enqueue(artist.getSongs());
                return true;
            case R.string.add_to_playlist:
                AddToPlaylistDialog.create(new ArrayList<>(artist.getSongs())).show(activity.getSupportFragmentManager(), "ADD_PLAYLIST");
                return true;
        }
        return false;
    }

    public static boolean handleMenuClick(@NonNull AppCompatActivity activity, @NonNull Playlist playlist, int string_res_option) {
        switch (string_res_option) {
            case R.string.play_next:
                MusicPlayerRemote.playNext(new ArrayList<>(MusicUtil.getPlaylistSongList(activity, playlist,"")));
                return true;
            case R.string.play_preview:
                if(activity instanceof AppActivity) {
                    SongPreviewController preview =((AppActivity) activity).getSongPreviewController();
                    if(preview!=null) {
                        if (preview.isPlayingPreview())
                            preview.cancelPreview();
                        else {

                            ArrayList<Song> list = new ArrayList<>(MusicUtil.getPlaylistSongList(activity, playlist,""));
                            Collections.shuffle(list);
                            preview.previewSongs(list);
                        }
                    }
                }
                return true;
            case R.string.add_to_queue:
                MusicPlayerRemote.enqueue(new ArrayList<>(MusicUtil.getPlaylistSongList(activity, playlist,"")));
                return true;
            case R.string.add_playlist_to_playlist:
                AddToPlaylistDialog.create(new ArrayList<>(MusicUtil.getPlaylistSongList(activity, playlist,""))).show(activity.getSupportFragmentManager(), "ADD_PLAYLIST");
                return true;
            case R.string.rename:
                RenamePlaylistDialog.create(playlist.id).show(activity.getSupportFragmentManager(), "RENAME_PLAYLIST");
                return true;
            case R.string.delete_from_playlist:
                DeletePlaylistDialog.create(playlist).show(activity.getSupportFragmentManager(), "DELETE_PLAYLIST");
                return true;
            case R.string.save_as:
                new SavePlaylistAsyncTask(activity).execute(playlist);
                return true;
        }
        return false;
    }


    private static class SavePlaylistAsyncTask extends WeakContextAsyncTask<Playlist, String, String> {
        public SavePlaylistAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected String doInBackground(Playlist... params) {
            try {
                return String.format(App.getInstance().getApplicationContext().getString(R.string.saved_playlist_to), PlaylistsUtil.savePlaylist(App.getInstance().getApplicationContext(), params[0]));
            } catch (Exception e) {
                e.printStackTrace();
                return String.format(App.getInstance().getApplicationContext().getString(R.string.failed_to_save_playlist), e);
            }
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            Context context = getContext();
            if (context != null) {
                Toast.makeText(context, string, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static boolean handleMenuClick(@NonNull AppCompatActivity activity, @NonNull Object object, int string_res_option) {
        if(object instanceof Song) {
            return SongMenuHelper.handleMenuClick(activity,(Song)object,string_res_option);
        } else if (object instanceof Playlist)
            return handleMenuClick(activity,(Playlist) object,string_res_option);
          else if(object instanceof Artist)
              return handleMenuClick(activity,(Artist)object,string_res_option);
        return false;
    }
}
