package org.pb.android.uxdesign.ui.button;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EView;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.util.Util;

@EView
public class SensorStateButton extends AppCompatImageView {

    public SensorStateButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterInject
    public void initView() {
        setBackgroundResource(R.drawable.sensor_state_button_animation);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
                frameAnimation.start();
            }
        }, Util.getRandomBetween(250, 1250));
    }
}
