package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.ui.ViewMode;
import org.pb.android.uxdesign.ui.button.MenueButtonView;

import java.util.List;

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

    @ViewsById({R.id.n2SquarePattern, R.id.o2SquarePattern, R.id.arSquarePattern, R.id.co2SquarePattern})
    List<SquarePatternView> squarePatternViewList;

    private boolean initiated;

    public UnitInfoMenuView(Context context) {
        super(context);
    }

    @AfterViews
    public void initView() {
        btnLeft.setText(R.string.menu_button_passenger_info);
        btnCenter.setText(R.string.menu_button_hpod_overview);
        btnRight.setText(R.string.menu_button_maintenance);

        initiated = true;
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

    public void startConsole() {
        processingConsole.start();
    }

    public void stopConsole() {
        processingConsole.stop();
    }

    public void refresh() {
        if (initiated) {
            for (SquarePatternView squarePatternView : squarePatternViewList) {
                squarePatternView.refresh();
            }
        }
    }
}
