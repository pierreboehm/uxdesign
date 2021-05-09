package org.pb.android.uxdesign.ui.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.ui.view.AccessCodeView;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_menue_access_system)
public class MenueAccessSystemView extends LinearLayout {

    @ViewById(R.id.accessCodeView)
    AccessCodeView accessCodeView;

    public MenueAccessSystemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Bitmap getAccessCodeBitmap() {
        return accessCodeView.getDrawingCacheBitmap();
    }

}
