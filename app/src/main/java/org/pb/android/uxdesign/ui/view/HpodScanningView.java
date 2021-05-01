package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.ui.button.ButtonView;

@EViewGroup(R.layout.view_hpod_scanning)
public class HpodScanningView extends LinearLayout {

    @ViewById(R.id.scanningView)
    ScanningView scanningView;

    @ViewById(R.id.bvFooterText)
    ButtonView bvFooterText;

    public HpodScanningView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initView() {
        bvFooterText.setText("HPOD STATUS SYS");
    }

    public void startScanning() {
        scanningView.start();
    }

    public void stopScanning() {
        scanningView.stop();
    }
}
