package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_unit_info_top_content)
public class UnitInfoTopContentView extends RelativeLayout {

    @ViewById(R.id.contentViewContainer)
    ViewGroup contentViewContainer;

    @ViewById(R.id.tvMainText)
    TextView tvMainText;

    public UnitInfoTopContentView(Context context) {
        super(context);
    }

    @AfterViews
    public void initView() {
        tvMainText.setText(R.string.hpod_unit_system);
    }

    public void clearContent() {
        contentViewContainer.removeAllViews();
    }

    public void setContentView(View view) {
        contentViewContainer.removeAllViews();
        contentViewContainer.addView(view);
    }

    public void addContentView(View view) {
        contentViewContainer.addView(view);
    }
}
