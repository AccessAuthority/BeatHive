package com.ldt.BeatHive.ui.maintab.feature;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldt.BeatHive.R;
import com.ldt.BeatHive.common.AppConfig;
import com.ldt.BeatHive.notification.EventKey;
import com.ldt.BeatHive.service.MusicServiceEventListener;
import com.ldt.BeatHive.ui.AppActivity;
import com.ldt.BeatHive.ui.CardLayerController;
import com.ldt.BeatHive.ui.floating.SearchFragment;
import com.ldt.BeatHive.ui.maintab.CardLayerFragment;
import com.ldt.BeatHive.ui.maintab.MusicServiceNavigationFragment;
import com.ldt.BeatHive.ui.maintab.subpages.viewplaylist.ViewPlaylistCardLayerFragment;
import com.ldt.BeatHive.loader.medialoader.PlaylistLoader;
import com.ldt.BeatHive.loader.medialoader.SongLoader;
import com.ldt.BeatHive.model.Playlist;
import com.ldt.BeatHive.ui.maintab.subpages.viewplaylist.ViewPlaylistFragment;
import com.zalo.gitlabmobile.notification.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeatureTabFragment extends MusicServiceNavigationFragment implements FeaturePlaylistAdapter.PlaylistClickListener, MusicServiceEventListener {
    private static final String TAG = "FeatureTabFragment";

    @BindView(R.id.status_bar)
    View mStatusView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.scroll_view)
    NestedScrollView mNestedScrollView;

    @OnClick(R.id.search)
    void showSearchScreen() {
        new SearchFragment().show(getParentFragmentManager(), "search");
        /*if (getActivity() instanceof AppActivity) {
            CardLayerController.CardLayerAttribute attribute = ((AppActivity) getActivity()).getCardLayerController().addCardLayerFragment(ViewPlaylistCardLayerFragment.newInstance(PlaylistLoader.getAllPlaylistsWithAuto(requireContext()).get(0), null), 0);
            attribute.animateToMax();
        }*/
    }

    FeatureLinearHolder mFeatureLinearHolder;

    @Nullable
    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.screen_feature_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if(AppConfig.hideIncompleteFeature) {
            view.findViewById(R.id.list_type).setVisibility(View.GONE);
            //view.findViewById(R.id.search).setVisibility(View.GONE);
        }
        
        mSwipeRefreshLayout.setColorSchemeResources(R.color.flatOrange);
        mSwipeRefreshLayout.setOnRefreshListener(this::refreshData);
        mSwipeRefreshLayout.setEnabled(false);

        mFeatureLinearHolder = new FeatureLinearHolder(getActivity(), mNestedScrollView);
        mFeatureLinearHolder.setPlaylistItemClick(this);
        updateInsets();
        refreshData();
    }

    private void updateInsets() {
        final int[] insets = AppConfig.getSystemBarsInset();
        mNestedScrollView.setPadding(insets[0], 0, insets[2], (int) (insets[3] + mNestedScrollView.getResources().getDimension(R.dimen.bottom_back_stack_spacing)));
        mStatusView.getLayoutParams().height = insets[1];
    }

    private void refreshData() {

        if (getActivity() != null) {
            mFeatureLinearHolder.setSuggestedPlaylists(PlaylistLoader.getAllPlaylistsWithAuto(getActivity()));
            mFeatureLinearHolder.setSuggestedSongs(SongLoader.getAllSongs(getActivity()));
        }

        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onSetStatusBarMargin(int value) {
        if (mStatusView != null) {
            mStatusView.getLayoutParams().height = value;
            mStatusView.requestLayout();
        }
    }

    @Override
    public void onClickPlaylist(Playlist playlist, @org.jetbrains.annotations.Nullable Bitmap bitmap) {
        boolean showInCardLayer = false;

        if(!showInCardLayer) {
            getNavigationController().presentFragment(ViewPlaylistFragment.newInstance(playlist, bitmap));
        } else if (getActivity() instanceof AppActivity) {
            CardLayerFragment sf = ViewPlaylistCardLayerFragment.newInstance(playlist, bitmap);

            CardLayerController.CardLayerAttribute attribute = ((AppActivity) getActivity()).getCardLayerController().addCardLayerFragment(sf, 0);
            attribute.expandImmediately();
            attribute.animateToMax();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        if(event.getKey() == EventKey.OnSystemBarsInsetUpdated.INSTANCE) {
           updateInsets();
        }
    }

    @Override
    public void onMediaStoreChanged() {
        refreshData();
    }

}
