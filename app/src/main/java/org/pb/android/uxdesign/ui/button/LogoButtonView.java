package org.pb.android.uxdesign.ui.button;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;

import android.util.AttributeSet;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_logo_button)
public class LogoButtonView extends LinearLayout {

    private static final String TAG = LogoButtonView.class.getSimpleName();

    private static final long ANIMATION_SPEED = 1000L;
    private static final float ALPHA_MIN = .3f;
    private static final float ALPHA_MAX = 1f;

    @ViewById(R.id.ivLogo)
    ImageView ivLogo;

    @ViewById(R.id.tvLogo)
    TextView tvLogo;

    private ValueAnimator animator;
    private boolean hasWaitingState;

    public LogoButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDetachedFromWindow() {
        hideWaitStatus();
        super.onDetachedFromWindow();
    }

    public boolean isSelected() {
        return ivLogo.isSelected();
    }

    public void setSelected(boolean selected) {
        ivLogo.setSelected(selected);

        if (selected) {
            hideWaitStatus();
            ivLogo.setColorFilter(getContext().getColor(R.color.blue_cyan));
            tvLogo.setTextColor(getContext().getColor(R.color.blue_cyan));
        } else {
            ivLogo.clearColorFilter();
            tvLogo.setTextColor(getContext().getColor(R.color.blue_light));
            showWaitStatus(ALPHA_MIN, ALPHA_MAX);
        }
    }

    // FIXME: is there a more elegant way to solve that?
    public void setWaitingState(boolean hasWaitingState) {
        this.hasWaitingState = hasWaitingState;
    }

    public boolean hasWaitingState() {
        return hasWaitingState;
    }

    private void showWaitStatus(float startValue, float endValue) {
        if (!hasWaitingState) {
            return;
        }

        animator = ValueAnimator.ofFloat(startValue, endValue);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                ivLogo.setAlpha(alpha);
                tvLogo.setAlpha(alpha);
            }
        });

        animator.setDuration(ANIMATION_SPEED);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    private void hideWaitStatus() {
        if (animator != null) {
            animator.end();
            animator.cancel();
            animator = null;
        }
    }
}
