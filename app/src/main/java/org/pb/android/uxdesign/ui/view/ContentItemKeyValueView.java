package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_content_item_key_value)
public class ContentItemKeyValueView extends RelativeLayout {

    @ViewById(R.id.tvKey)
    TextView tvKey;

    @ViewById(R.id.tvValue)
    TextView tvValue;

    @ViewById(R.id.tvDots)
    TextView tvDots;

    public ContentItemKeyValueView(Context context) {
        super(context);
    }

    public void bind(String key, @Nullable String value) {
        tvKey.setText(key);
        tvValue.setText(value);
        tvDots.setVisibility((value == null || value.length() == 0) ? INVISIBLE : VISIBLE);
    }

    public void bind(int resourceId, @Nullable String value) {
        tvKey.setText(resourceId);
        tvValue.setText(value);
        tvDots.setVisibility((value == null || value.length() == 0) ? INVISIBLE : VISIBLE);
    }
}
