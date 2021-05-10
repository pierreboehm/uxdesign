package org.pb.android.uxdesign.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.ui.button.ButtonView;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_hpod_processing)
public class HpodProcessingView extends LinearLayout {

    private static final String TAG = HpodProcessingView.class.getSimpleName();

    private static final int ANIMATION_SPEED_IN_MILLISECONDS = 500;
    private static final float NOT_TRANSPARENT = 1f;
    private static final float SEMI_TRANSPARENT = .5f;

    @ViewById(R.id.tvStatusSystem)
    TextView tvStatusSystem;

    @ViewById(R.id.progressBarStatusSystem)
    ProgressBar progressBarStatusSystem;

    @ViewById(R.id.tvDataProcessing)
    TextView tvDataProcessing;

    @ViewById(R.id.progressBarDataProcessing)
    ProgressBar progressBarDataProcessing;

    @ViewById(R.id.tvDataMonitoring)
    TextView tvDataMonitoring;

    @ViewById(R.id.progressBarDataMonitoring)
    ProgressBar progressBarDataMonitoring;

    @ViewById(R.id.bvFooterText)
    ButtonView bvFooterText;

    public HpodProcessingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setProgressStatusSystem(int progressStatusSystem) {
        progressBarStatusSystem.setProgress(progressStatusSystem);
    }

    public void setProgressDataProcessing(int progressDataProcessing) {
        progressBarDataProcessing.setProgress(progressDataProcessing);
    }

    public void setProgressDataMonitoring(int progressDataMonitoring) {
        progressBarDataMonitoring.setProgress(progressDataMonitoring);
    }

    public void startProcessing() {
        tvStatusSystem.setAlpha(NOT_TRANSPARENT);
        tvDataProcessing.setAlpha(NOT_TRANSPARENT);
        tvDataMonitoring.setAlpha(NOT_TRANSPARENT);
    }

    public void stopProcessing() {
        animateProgressShutDown(progressBarStatusSystem.getProgress(),
                tvStatusSystem, progressBarStatusSystem);

        animateProgressShutDown(progressBarDataProcessing.getProgress(),
                tvDataProcessing, progressBarDataProcessing);

        animateProgressShutDown(progressBarDataMonitoring.getProgress(),
                tvDataMonitoring, progressBarDataMonitoring);
    }

    private void animateProgressShutDown(int startValue, TextView textView, ProgressBar progressBar) {
        ValueAnimator animator = ValueAnimator.ofInt(startValue, 0);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                progressBar.setProgress(value);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                textView.setAlpha(SEMI_TRANSPARENT);
            }
        });

        animator.setDuration(ANIMATION_SPEED_IN_MILLISECONDS);
        animator.start();
    }
}
