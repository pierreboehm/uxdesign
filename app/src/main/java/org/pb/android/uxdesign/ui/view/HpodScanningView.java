package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.pb.android.uxdesign.R;

@EViewGroup(R.layout.view_hpod_scanning)
public class HpodScanningView extends LinearLayout {

    public HpodScanningView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
