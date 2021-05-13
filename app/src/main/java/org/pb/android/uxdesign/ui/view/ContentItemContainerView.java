package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_content_item_container)
public class ContentItemContainerView extends LinearLayout {

    @ViewById(R.id.contentItemViewContainer)
    ViewGroup contentItemViewContainer;

    public ContentItemContainerView(Context context) {
        super(context);
    }

    public void setView(View view) {
        contentItemViewContainer.removeAllViews();
        contentItemViewContainer.addView(view);
    }

    public void addView(View view) {
        contentItemViewContainer.addView(view);
    }

}
