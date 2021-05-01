package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.ui.button.ButtonView;

@EViewGroup(R.layout.view_hpod_system_status)
public class HpodSystemStatusView extends LinearLayout {

    @ViewById(R.id.dotPatternView)
    DotPatternBigView dotPatternView;

    @ViewById(R.id.bvSystemStatusHeaderText)
    ButtonView bvSystemStatusHeaderText;

    @ViewById(R.id.tvSystemStatusFooterText)
    TextView tvSystemStatusFooterText;

    public HpodSystemStatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void preparePowerSystemStatusScreen() {
        bvSystemStatusHeaderText.setText("PS");
        tvSystemStatusFooterText.setText("POWER SYSTEM");
        dotPatternView.setDotPattern(DotPatternBigView.DotPattern.TRIANGLE);
    }

    public void prepareCpuSystemStatusScreen() {
        bvSystemStatusHeaderText.setText("CPU");
        tvSystemStatusFooterText.setText("PROCESSES");
        dotPatternView.setDotPattern(DotPatternBigView.DotPattern.SQUARE);
    }

    public void prepareTempSystemStatusScreen() {
        bvSystemStatusHeaderText.setText("TPS");
        tvSystemStatusFooterText.setText("TEMP SYSTEM");
        dotPatternView.setDotPattern(DotPatternBigView.DotPattern.CIRCLE);
    }
}
