package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@EViewGroup(R.layout.view_hpod_scanning)
public class HpodScanningView extends LinearLayout {

    @ViewById(R.id.scanningView)
    ScanningView scanningView;

    public HpodScanningView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void startScanning() {
        scanningView.start();
    }

    public void stopScanning() {
        scanningView.stop();
    }
}
