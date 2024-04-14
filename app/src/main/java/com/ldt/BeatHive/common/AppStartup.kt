package com.ldt.BeatHive.common

import androidx.annotation.WorkerThread
import com.ldt.BeatHive.interactors.AppExecutors

object AppStartup {
    @JvmStatic
    fun onAppStartup() {
        AppExecutors.single().execute {
            initDataAsyncOnStartUp()
        }
    }

    @WorkerThread
    private fun initDataAsyncOnStartUp() {
        MediaManager.loadMediaIfNeeded()
    }
}