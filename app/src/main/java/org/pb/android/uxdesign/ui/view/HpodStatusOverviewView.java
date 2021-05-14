package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewsById;
import org.pb.android.uxdesign.R;

import java.util.List;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_hpod_status_overview)
public class HpodStatusOverviewView extends LinearLayout {

    @ViewsById({R.id.powerSystemDotPattern, R.id.cpuSystemDotPattern, R.id.tempSystemDotPattern})
    List<DotPatternSmallView> dotPatternSmallViewList;

    private boolean initiated;

    public HpodStatusOverviewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initView() {
        initiated = true;
    }

    public void refresh() {
        if (initiated) {
            for (DotPatternSmallView dotPatternSmallView : dotPatternSmallViewList) {
                dotPatternSmallView.refresh();
            }
        }
    }
}
