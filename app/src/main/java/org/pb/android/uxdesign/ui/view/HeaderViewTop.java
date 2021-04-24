package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EViewGroup;
import org.pb.android.uxdesign.R;

@EViewGroup(R.layout.header_view_top)
public class HeaderViewTop extends LinearLayout {

    public HeaderViewTop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
