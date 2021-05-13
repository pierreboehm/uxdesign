package org.pb.android.uxdesign.ui.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewsById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.pb.android.uxdesign.AppPreferences_;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.ui.UnitInfoMenuConfiguration;

import java.util.List;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_unit_info_menue_top)
public class UnitInfoTopMenuView extends RelativeLayout {

    private static final long ANIMATION_SPEED = 1000L;
    private static final int ALPHA_MIN = 96;
    private static final int ALPHA_MAX = 255;

    @ViewsById({R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5})
    List<ImageButton> buttonGroup1List;

    @ViewsById({R.id.btn_1_1, R.id.btn_1_2})
    List<ImageButton> buttonGroup2_1List;

    @ViewsById({R.id.btn_2_2})
    List<ImageButton> buttonGroup2_2List;

    @ViewsById({R.id.btn_4_2})
    List<ImageButton> buttonGroup2_4List;

    @ViewsById({R.id.btn_5_2})
    List<ImageButton> buttonGroup2_5List;

    @Pref
    AppPreferences_ preferences;

    private ValueAnimator animator;
    private ImageButton lastButtonSelected;

    public UnitInfoTopMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if (visibility == VISIBLE) {
            performSelection(buttonGroup1List.get(preferences.selectedButton().get()));
        } else {
            hideSelectedStatus();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        hideSelectedStatus();
        super.onDetachedFromWindow();
    }

    @Click({R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5})
    public void onButtonGroup1Click(ImageButton button) {
        performSelection(button);
    }

    @Click({R.id.btn_1_1, R.id.btn_1_2, R.id.btn_2_2, R.id.btn_4_2, R.id.btn_5_2})
    public void onButtonGroup2Click(ImageButton button) {
        performSelection(button);
    }

    private void performSelection(ImageButton button) {
        unselectAll(button.getId());
        button.setSelected(!button.isSelected());

        UnitInfoMenuConfiguration unitInfoMenuConfiguration = UnitInfoMenuConfiguration.getButtonById(button.getId());
        if (unitInfoMenuConfiguration != null) {
            unitInfoMenuConfiguration.onClick(button.isSelected());
        }

        // store preselection in preferences
        int indexOfMainButtonClicked = indexOf(button.getId());         // identity only main-buttons!
        if (indexOfMainButtonClicked != -1) {
            preferences.selectedButton().put(indexOfMainButtonClicked);

            hideSelectedStatus();
            if (button.isSelected()) {
                showSelectedStatus(ALPHA_MAX, ALPHA_MIN, button);
                lastButtonSelected = button;
            }
        }
    }

    private void unselectAll(int skipId) {
        unselect(buttonGroup1List, skipId);
        unselect(buttonGroup2_1List, null);
        unselect(buttonGroup2_2List, null);
        unselect(buttonGroup2_4List, null);
        unselect(buttonGroup2_5List, null);
    }

    private void unselect(List<ImageButton> imageButtonList, @Nullable Integer skipId) {
        for (ImageButton imageButton : imageButtonList) {
            if (skipId == null || imageButton.getId() != skipId) {
                imageButton.setSelected(false);
            }
            imageButton.setImageAlpha(ALPHA_MAX);
        }
    }

    private int indexOf(int buttonId) {
        for (int index = 0; index < buttonGroup1List.size(); index++) {
            ImageButton imageButton = buttonGroup1List.get(index);
            if (imageButton.getId() == buttonId) {
                return index;
            }
        }
        return -1;
    }

    private void showSelectedStatus(int startValue, int endValue, ImageButton imageButton) {

        if (lastButtonSelected != null && lastButtonSelected.getId() != imageButton.getId()) {
            lastButtonSelected.setImageAlpha(ALPHA_MAX);
        }

        animator = ValueAnimator.ofInt(startValue, endValue);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = (int) animation.getAnimatedValue();
                imageButton.setImageAlpha(alpha);
            }
        });

        animator.setDuration(ANIMATION_SPEED);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    private void hideSelectedStatus() {
        if (animator != null) {
            animator.end();
            animator.cancel();
            animator = null;
        }
    }
}
