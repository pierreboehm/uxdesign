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
import org.pb.android.uxdesign.ui.ViewMode;
import org.pb.android.uxdesign.ui.button.MenueButtonView;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_unit_info_menu)
public class UnitInfoMenuView extends RelativeLayout {

    @ViewById(R.id.btnLeft)
    MenueButtonView btnLeft;

    @ViewById(R.id.btnCenter)
    MenueButtonView btnCenter;

    @ViewById(R.id.btnRight)
    MenueButtonView btnRight;

    @ViewById(R.id.processingConsoleView)
    ProcessingConsoleView processingConsole;

    public UnitInfoMenuView(Context context) {
        super(context);
    }

    @AfterViews
    public void initView() {
        btnLeft.setText(R.string.menu_button_passenger_info);
        btnCenter.setText(R.string.menu_button_hpod_overview);
        btnRight.setText(R.string.menu_button_maintenance);
    }

    public void startConsole() {
        processingConsole.start();
    }

    public void stopConsole() {
        processingConsole.stop();
    }

    @Click(R.id.btnLeft)
    public void onButtonLeftClick() {
        EventBus.getDefault().post(new Event.ShowVitalStatus());
    }

    @Click(R.id.btnCenter)
    public void onButtonCenterClick() {
        EventBus.getDefault().post(new Event.ShowUserStatus());
    }

    @Click(R.id.btnRight)
    public void onButtonRightClick() {
        EventBus.getDefault().post(new Event.ShowDialog(ViewMode.PASSENGER_INFO));
    }
}
