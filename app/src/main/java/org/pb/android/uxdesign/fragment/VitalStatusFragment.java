package org.pb.android.uxdesign.fragment;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.Demonstrator;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.ui.view.HpodFooter;
import org.pb.android.uxdesign.ui.view.HpodHeader;
import org.pb.android.uxdesign.ui.view.HpodProcessingView;
import org.pb.android.uxdesign.ui.view.HpodScanningView;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_vital_status)
public class VitalStatusFragment extends Fragment {

    public static final String TAG = VitalStatusFragment.class.getSimpleName();

    @ViewById(R.id.hpodHeader)
    HpodHeader hpodHeader;

    @ViewById(R.id.hpodFooter)
    HpodFooter hpodFooter;

    @ViewById(R.id.hpodProcessingView)
    HpodProcessingView hpodProcessingView;

    @ViewById(R.id.hpodScanningView)
    HpodScanningView hpodScanningView;

    @Bean
    Demonstrator demonstrator;

    @AfterViews
    public void initViews() {
        CurrentUser currentUser = demonstrator.getCurrentUser();
        hpodHeader.setCurrentUser(currentUser);

        hpodHeader.prepareVitalStatusScreen();
        hpodFooter.prepareVitalStatusScreen();

        // FIXME: adapt measured margin from parent layout, so parent container has same surrounding space
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

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onEvent(Event.BpmUpdate event) {
        hpodFooter.updateBpmValue(event.getBpmValue());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.StatusSystemProgressUpdate event) {
        hpodProcessingView.setProgressStatusSystem(event.getStatusSystemProgress());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.DataProcessingProgressUpdate event) {
        hpodProcessingView.setProgressDataProcessing(event.getDataProcessingProgress());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.DataMonitoringProgressUpdate event) {
        hpodProcessingView.setProgressDataMonitoring(event.getDataMonitoringProgress());
    }

    @Click(R.id.ivLogo)
    public void onLogoClick() {
        if (hpodHeader.isDischargedState()) {
            hpodHeader.setDischargedState(false, true);

            hpodProcessingView.startProcessing();
            hpodScanningView.startScanning();

            hpodFooter.startVitalGraph();
        } else {
            hpodHeader.setDischargedState(true, true);

            hpodProcessingView.stopProcessing();
            hpodScanningView.stopScanning();

            hpodFooter.stopVitalGraph();
        }
    }
}
