package org.pb.android.uxdesign.ui.button;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.pb.android.uxdesign.R;

@EViewGroup(R.layout.view_logo_button)
public class LogoButtonView extends LinearLayout {

    public LogoButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
