package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_hpod_footer)
public class HpodFooter extends RelativeLayout {

    @ViewById(R.id.viewContainer)
    ViewGroup viewContainer;

    private VitalStatusView vitalStatusView;

    public HpodFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void prepareMainScreen() {
        viewContainer.removeAllViews();

        MainMenuView mainMenuView = MainMenuView_.build(getContext());
        viewContainer.addView(mainMenuView);
    }

    public void prepareVitalStatusScreen() {
        viewContainer.removeAllViews();

        vitalStatusView = VitalStatusView_.build(getContext());
        viewContainer.addView(vitalStatusView);
    }

    public void prepareSystemStatusScreen() {
        viewContainer.removeAllViews();

        // TODO: add system-status-view
        // FIXME: main menu view is just for test !!!
        MainMenuView mainMenuView = MainMenuView_.build(getContext());
        viewContainer.addView(mainMenuView);
    }

    public void startVitalGraph() {
        if (vitalStatusView != null) {
            vitalStatusView.startVitalGraph();
        }
    }

    public void stopVitalGraph() {
        if (vitalStatusView != null) {
            vitalStatusView.stopVitalGraph();
        }
    }

    public void updateBpmValue(int bpmValue) {
        if (vitalStatusView != null) {
            vitalStatusView.updateBpmValue(bpmValue);
        }
    }
}
