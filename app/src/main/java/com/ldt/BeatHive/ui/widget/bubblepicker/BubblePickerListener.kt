package com.ldt.BeatHive.ui.widget.bubblepicker

import com.ldt.BeatHive.ui.widget.bubblepicker.model.PickerItem

/**
 * Created by irinagalata on 3/6/17.
 */
interface BubblePickerListener {

    fun onBubbleSelected(item: PickerItem, position: Int)

    fun onBubbleDeselected(item: PickerItem, position: Int)

}