package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.ui.ViewMode;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_hpod_footer)
public class HpodFooter extends RelativeLayout {

    @ViewById(R.id.footerViewContainer)
    ViewGroup viewContainer;

    private ViewMode viewMode;

    private MainMenuView mainMenuView;
    private VitalStatusView vitalStatusView;
    private UnitInfoMenuView unitInfoMenuView;

    public HpodFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void prepareScreen(ViewMode viewMode) {
        this.viewMode = viewMode;

        switch (viewMode) {
            case MAIN: {
                prepareMainScreen();
                break;
            }
            case PASSENGER_INFO: {
                prepareVitalStatusScreen();
                break;
            }
            case UNIT_INFO: {
                prepareSystemStatusScreen();
                break;
            }
            case MAINTENANCE: {
                break;
            }
        }
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

    public void startConsole() {
        if (unitInfoMenuView != null) {
            unitInfoMenuView.startConsole();
        }
    }

    public void stopConsole() {
        if (unitInfoMenuView != null) {
            unitInfoMenuView.stopConsole();
        }
    }

    public void updateBpmValue(int bpmValue) {
        if (vitalStatusView != null) {
            vitalStatusView.updateBpmValue(bpmValue);
        }
    }

    public void refresh() {
        if (mainMenuView != null) {
            mainMenuView.refresh();

        } else if (unitInfoMenuView != null) {
            unitInfoMenuView.refresh();
        }
    }

    private void prepareMainScreen() {
        viewContainer.removeAllViews();

        vitalStatusView = null;
        unitInfoMenuView = null;

        mainMenuView = MainMenuView_.build(getContext());
        viewContainer.addView(mainMenuView);
    }

    private void prepareVitalStatusScreen() {
        viewContainer.removeAllViews();

        mainMenuView = null;
        unitInfoMenuView = null;

        vitalStatusView = VitalStatusView_.build(getContext());
        viewContainer.addView(vitalStatusView);
    }

    private void prepareSystemStatusScreen() {
        viewContainer.removeAllViews();

        mainMenuView = null;
        vitalStatusView = null;

        unitInfoMenuView = UnitInfoMenuView_.build(getContext());
        viewContainer.addView(unitInfoMenuView);
    }
}
