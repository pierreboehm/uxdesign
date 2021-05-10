package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_content_item_text)
public class ContentItemTextView extends FrameLayout {

    @ViewById(R.id.tvText)
    TextView tvText;

    public ContentItemTextView(@NonNull Context context) {
        super(context);
    }

    public void setText(String text) {
        tvText.setTypeface(Typeface.DEFAULT);
        tvText.setText(text);
    }

    public void setBoldText(String text) {
        tvText.setTypeface(Typeface.DEFAULT_BOLD);
        tvText.setText(text);
    }
}
