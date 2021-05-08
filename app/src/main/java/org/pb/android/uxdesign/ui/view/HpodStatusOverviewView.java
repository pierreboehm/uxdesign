package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_hpod_status_overview)
public class HpodStatusOverviewView extends LinearLayout {

    public HpodStatusOverviewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
