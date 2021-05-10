package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_unit_info_top_content)
public class UnitInfoTopContentView extends RelativeLayout {

    @ViewById(R.id.contentViewContainer)
    ViewGroup contentViewContainer;

    public UnitInfoTopContentView(Context context) {
        super(context);
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
