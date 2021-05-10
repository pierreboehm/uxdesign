package org.pb.android.uxdesign.ui.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.button_open_hexagon_with_dot)
public class ButtonView extends LinearLayout {

    @ViewById(R.id.tvText)
    TextView tvText;

    public ButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(int resourceId) {
        tvText.setText(resourceId);
    }

    public void setText(String text) {
        tvText.setText(text);
    }
}
