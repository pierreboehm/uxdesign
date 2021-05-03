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

    @ViewById(R.id.dotPatternBigView)
    DotPatternBigView dotPatternBigView;

    @ViewById(R.id.dotPatternSmallView)
    DotPatternSmallView dotPatternSmallView;

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
        dotPatternBigView.setDotPattern(DotPatternBigView.DotPattern.TRIANGLE);
    }

    public void prepareCpuSystemStatusScreen() {
        bvSystemStatusHeaderText.setText("CPU");
        tvSystemStatusFooterText.setText("PROCESSES");
        dotPatternBigView.setDotPattern(DotPatternBigView.DotPattern.SQUARE);
    }

    public void prepareTempSystemStatusScreen() {
        bvSystemStatusHeaderText.setText("TPS");
        tvSystemStatusFooterText.setText("TEMP SYSTEM");
        dotPatternBigView.setDotPattern(DotPatternBigView.DotPattern.CIRCLE);
    }
}
