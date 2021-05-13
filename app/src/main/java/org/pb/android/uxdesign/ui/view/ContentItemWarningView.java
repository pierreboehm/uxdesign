package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_content_item_warning)
public class ContentItemWarningView extends LinearLayout {

    @ViewById(R.id.tvWarningTitle)
    TextView tvWarningTitle;

    @ViewById(R.id.tvWarningText)
    TextView tvWarningText;

    public ContentItemWarningView(Context context) {
        super(context);
    }

    public void setWarningTitle(String text) {
        tvWarningTitle.setText(text);
    }

    public void setWarningTitle(int resourceId) {
        tvWarningTitle.setText(resourceId);
    }

    public void setWarningText(String text) {
        tvWarningText.setText(text);
    }

    public void setWarningText(int resourceId) {
        tvWarningText.setText(resourceId);
    }
}
