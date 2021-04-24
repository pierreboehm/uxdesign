package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.EViewGroup;
import org.pb.android.uxdesign.R;

@EViewGroup(R.layout.view_square)
public class SquareView extends RelativeLayout {

    public SquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
