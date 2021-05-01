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
@EViewGroup(R.layout.view_menue_button)
public class MenueButtonView extends LinearLayout {

    @ViewById(R.id.buttonText)
    TextView buttonText;

    public MenueButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(String text) {
        buttonText.setText(text);
    }
}
