package com.ldt.BeatHive.ui.widget.bubblepicker;

import android.content.Context;

import com.ldt.BeatHive.ui.widget.bubblepicker.model.PickerItem;
import com.ldt.BeatHive.ui.widget.bubblepicker.physics.PhysicsEngine;
import com.ldt.BeatHive.ui.widget.bubblepicker.rendering.PickerAdapter;

import java.util.Random;

public class SampleAdapter extends PickerAdapter<String> {

    public SampleAdapter(Context context) {
        super(context);
    }
    Random rnd = new Random();
    @Override
    public boolean onBindItem(PickerItem item, boolean create, int i) {
        super.onBindItem(item,create,i);
        String name = mData.get(i);
        item.setTitle(name);
        item.setRadiusUnit(PhysicsEngine.INSTANCE.interpolate(1,2f,((float) rnd.nextInt(getItemCount()))/getItemCount()));

        return true;
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
