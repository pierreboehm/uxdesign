package org.pb.android.uxdesign.ui.button;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EView;
import org.pb.android.uxdesign.R;

@EView
public class ProcessingStateButton extends androidx.appcompat.widget.AppCompatImageView {

    public ProcessingStateButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterInject
    public void initView() {
        setBackgroundResource(R.drawable.processing_state_button_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
        frameAnimation.start();
    }
}
