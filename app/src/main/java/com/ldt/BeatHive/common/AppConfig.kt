package com.ldt.BeatHive.common

import com.ldt.BeatHive.helper.extension.post
import com.ldt.BeatHive.notification.EventKey

object AppConfig {
    const val hideIncompleteFeature = true
    const val isShowSortingTileInLibrarySongTab = false
    const val isShowEmptyViewInLibrarySongTab = false
    const val isShowShuffleTileInLibrarySongTab = false
    const val isApplyOptimizedFlowOnPlayingStateChanged = false

    const val isSearchInterfaceByPassTouchEvent = true

    @JvmStatic
    var systemBarsInset = intArrayOf(0, 0, 0, 0)
    set(value) {
        System.arraycopy(value, 0, field, 0, 4)
        EventKey.OnSystemBarsInsetUpdated.post()
    }
}