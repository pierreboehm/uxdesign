package org.pb.android.uxdesign.ui.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@EViewGroup(R.layout.view_hpod_progress_value)
public class HpodProgressValueView extends RelativeLayout {

    private static final long ANIMATION_SPEED_IN_MILLISECONDS = 500L;

    @ViewById(R.id.progressBarProgressValue)
    ProgressBar progressBar;

    @ViewById(R.id.tvTextProgressValue)
    TextView tvProgressValue;

    @ViewById(R.id.tvSubTextTop)
    TextView tvSubTextTop;

    @ViewById(R.id.tvSubTextBottom)
    TextView tvSubTextBottom;

    public HpodProgressValueView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProgressValue(int progressValue) {
        animateProgressChange(progressBar.getProgress(), progressValue);
    }

    public void setTextTop(String text) {
        tvSubTextTop.setText(text);
    }

    public void setTextBottom(String text) {
        tvSubTextBottom.setText(text);
    }

    private void animateProgressChange(int startValue, int endValue) {
        ValueAnimator animator = ValueAnimator.ofInt(startValue, endValue);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                progressBar.setProgress(value);
                tvProgressValue.setText(String.format("%d%%", value));
            }
        });

        animator.setDuration(ANIMATION_SPEED_IN_MILLISECONDS);
        animator.start();
    }
}
