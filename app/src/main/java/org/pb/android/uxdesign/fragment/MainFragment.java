package org.pb.android.uxdesign.fragment;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.pb.android.uxdesign.data.Demonstrator;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.ui.ViewMode;
import org.pb.android.uxdesign.ui.view.HpodFooter;
import org.pb.android.uxdesign.ui.view.HpodHeader;
import org.pb.android.uxdesign.ui.view.HpodSystemStatusView;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    @ViewById(R.id.hpodHeader)
    HpodHeader hpodHeader;

    @ViewById(R.id.hpodFooter)
    HpodFooter hpodFooter;

    @ViewById(R.id.powerSystemStatusView)
    HpodSystemStatusView powerSystemStatusView;

    @ViewById(R.id.cpuSystemStatusView)
    HpodSystemStatusView cpuSystemStatusView;

    @ViewById(R.id.tempSystemStatusView)
    HpodSystemStatusView tempSystemStatusView;

    @Bean
    Demonstrator demonstrator;

    private boolean initiated;

    @AfterViews
    public void initViews() {
        CurrentUser currentUser = demonstrator.getCurrentUser();
        hpodHeader.setCurrentUser(currentUser);

        hpodHeader.prepareScreen(ViewMode.MAIN);
        hpodFooter.prepareScreen(ViewMode.MAIN);

        powerSystemStatusView.preparePowerSystemStatusScreen();
        cpuSystemStatusView.prepareCpuSystemStatusScreen();
        tempSystemStatusView.prepareTempSystemStatusScreen();

        initiated = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.UserDataUpdate event) {
        hpodHeader.setCurrentUser(new CurrentUser(event.getUserData()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.Refresh event) {
        if (initiated) {
            powerSystemStatusView.refresh();
            cpuSystemStatusView.refresh();
            tempSystemStatusView.refresh();
            hpodFooter.refresh();
        }
    }
}
