package com.ldt.BeatHive.ui.maintab.library.viewholder

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ldt.BeatHive.R
import com.ldt.BeatHive.utils.ViewUtils

class TopGradientDimViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(createItemView(parent)) {

    companion object {
        fun createItemView(parent: ViewGroup): View {
            val dimView = View(parent.context)
            dimView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtils.convertDipsToPixels(dimView.context, 96f))
            dimView.setBackgroundResource(R.drawable.search_top_gradient)
            return dimView
        }
    }
}

