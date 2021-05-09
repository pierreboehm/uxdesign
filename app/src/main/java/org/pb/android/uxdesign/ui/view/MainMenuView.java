package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.RelativeLayout;

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
@EViewGroup(R.layout.view_main_menu)
public class MainMenuView extends RelativeLayout {

    @ViewById(R.id.btnLeft)
    MenueButtonView btnLeft;

    @ViewById(R.id.btnCenter)
    MenueButtonView btnCenter;

    @ViewById(R.id.btnRight)
    MenueButtonView btnRight;

    @ViewById(R.id.dotPatternBigView)
    DotPatternBigView dotPatternBigView;

    @ViewById(R.id.dotPatternSmallView)
    DotPatternSmallView dotPatternSmallView;

    public MainMenuView(Context context) {
        super(context);
    }

    @AfterViews
    public void initView() {
        btnLeft.setText("PASSENGER INFO");
        btnCenter.setText("UNIT INFO");
        btnRight.setText("MAINTENANCE");

        dotPatternBigView.setDotPattern(DotPatternBigView.DotPattern.RANDOM);
        dotPatternSmallView.setDefaultColor(getContext().getColor(R.color.white));
    }

    @Click(R.id.btnLeft)
    public void onButtonLeftClick() {
        EventBus.getDefault().post(new Event.ShowVitalStatus());
    }

    @Click(R.id.btnCenter)
    public void onButtonCenterClick() {
        EventBus.getDefault().post(new Event.ShowSystemStatus());
    }

    @Click(R.id.btnRight)
    public void onButtonRightClick() {
        EventBus.getDefault().post(new Event.ShowDialog(ViewMode.PASSENGER_INFO));
    }
}
