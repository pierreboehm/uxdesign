package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.event.Event;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_main_menu)
public class MainMenuView extends RelativeLayout {

    public MainMenuView(Context context) {
        super(context);
    }

    @Click(R.id.btnVitalStatus)
    public void onButtonVitalStatusClick() {
        EventBus.getDefault().post(new Event.ShowVitalStatus());
    }

    @Click(R.id.btnSystemStatus)
    public void onButtonSystemStatusClick() {
        EventBus.getDefault().post(new Event.ShowSystemStatus());
    }
}
