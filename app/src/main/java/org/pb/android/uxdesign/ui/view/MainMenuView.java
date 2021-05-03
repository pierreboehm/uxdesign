package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.ui.button.MenueButtonView;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_main_menu)
public class MainMenuView extends RelativeLayout {

    @ViewById(R.id.btnPassengerInfo)
    MenueButtonView btnPassengerInfo;

    @ViewById(R.id.btnUnitInfo)
    MenueButtonView btnUnitInfo;

    @ViewById(R.id.btnMaintenance)
    MenueButtonView btnMaintenance;

    @ViewById(R.id.dotPatternBigView)
    DotPatternBigView dotPatternBigView;

    @ViewById(R.id.dotPatternSmallView)
    DotPatternSmallView dotPatternSmallView;

    public MainMenuView(Context context) {
        super(context);
    }

    @AfterViews
    public void initView() {
        btnPassengerInfo.setText("PASSENGER INFO");
        btnUnitInfo.setText("UNIT INFO");
        btnMaintenance.setText("MAINTENANCE");

        dotPatternBigView.setDotPattern(DotPatternBigView.DotPattern.RANDOM);
        dotPatternSmallView.setDefaultColor(getContext().getColor(R.color.white));
    }

    @Click(R.id.btnPassengerInfo)
    public void onButtonVitalStatusClick() {
        EventBus.getDefault().post(new Event.ShowVitalStatus());
    }

    @Click(R.id.btnUnitInfo)
    public void onButtonSystemStatusClick() {
        EventBus.getDefault().post(new Event.ShowSystemStatus());
    }

    @Click(R.id.btnMaintenance)
    public void onButtonMaintenanceClick() {
        Toast.makeText(getContext(), "onButtonMaintenanceClick()", Toast.LENGTH_LONG).show();
    }
}
