package org.pb.android.uxdesign.fragment;

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
import org.pb.android.uxdesign.ui.view.HpodFooter;
import org.pb.android.uxdesign.ui.view.HpodHeader;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    @ViewById(R.id.hpodHeader)
    HpodHeader hpodHeader;

    @ViewById(R.id.hpodFooter)
    HpodFooter hpodFooter;

    @Bean
    Demonstrator demonstrator;

    @AfterViews
    public void initViews() {
        CurrentUser currentUser = demonstrator.getCurrentUser();
        hpodHeader.setCurrentUser(currentUser);

        hpodHeader.prepareMainScreen();
        hpodFooter.prepareMainScreen();
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        //hpodView.stopVitalGraph();

        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Event.VitalGraph event) {
        EventBus.getDefault().removeStickyEvent(event);

        if (event.shouldStart()) {
            //hpodView.startVitalGraph();
        } else {
            //hpodView.stopVitalGraph();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Event.VitalStatusState event) {
        EventBus.getDefault().removeStickyEvent(event);

        if (event.isDischarged()) {
            //hpodView.stopVitalGraph();
        } else {
            //hpodView.startVitalGraph();
        }
    }
}
