package org.pb.android.uxdesign.fragment;

import android.annotation.SuppressLint;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.ContentManager;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.ui.ViewMode;
import org.pb.android.uxdesign.ui.view.HpodFooter;
import org.pb.android.uxdesign.ui.view.HpodHeader;
import org.pb.android.uxdesign.ui.view.HpodProgressValueView;
import org.pb.android.uxdesign.util.Util;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_system_status)
public class SystemStatusFragment extends Fragment {

    public static final String TAG = SystemStatusFragment.class.getSimpleName();

    private float N2MIN, N2MAX, O2MIN, O2MAX, CO2MIN, CO2MAX;
    private float nitrogenValue, oxygenValue, argonValue, carbonDioxydValue;

    @ViewById(R.id.hpodHeader)
    HpodHeader hpodHeader;

    @ViewById(R.id.hpodFooter)
    HpodFooter hpodFooter;

    @ViewById(R.id.progressValue1)
    HpodProgressValueView progressValueView1;

    @ViewById(R.id.progressValue2)
    HpodProgressValueView progressValueView2;

    @Bean
    ContentManager contentManager;

    @AfterInject
    public void afterInject() {
        N2MIN = ResourcesCompat.getFloat(getResources(), R.dimen.n2_min);
        N2MAX = ResourcesCompat.getFloat(getResources(), R.dimen.n2_max);

        O2MIN = ResourcesCompat.getFloat(getResources(), R.dimen.o2_min);
        O2MAX = ResourcesCompat.getFloat(getResources(), R.dimen.o2_max);

        CO2MIN = ResourcesCompat.getFloat(getResources(), R.dimen.co2_min);
        CO2MAX = ResourcesCompat.getFloat(getResources(), R.dimen.co2_max);
    }

    @AfterViews
    public void initViews() {
        hpodHeader.prepareScreen(ViewMode.UNIT_INFO);
        hpodFooter.prepareScreen(ViewMode.UNIT_INFO);

        setupGases();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        hpodFooter.startConsole();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        hpodFooter.stopConsole();
        super.onPause();
    }

    @Click(R.id.progressValue1)
    void onProgressValue1Click() {
        setNitrogen(true);
    }

    @Click(R.id.progressValue2)
    void onProgressValue2Click() {
        setOxygen(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ContentClear event) {
        hpodHeader.clearContent();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Event.ContentShowApplicationInfo event) {
        EventBus.getDefault().removeStickyEvent(event);
        setApplicationInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ContentShowUserInfo event) {
        setUserInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ContentShowPowerInfo event) {
        setPowerInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ContentShowSustainmentInfo event) {
        setSustainmentInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ContentShowUnitInfo event) {
        setUnitInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ContentShowTimersInfo event) {
        setTimersInfo();
    }

    private void setApplicationInfo() {
        contentManager.setApplicationInfo(hpodHeader);
    }

    private void setUserInfo() {
        contentManager.setUserInfo(hpodHeader);
    }

    private void setPowerInfo() {
        contentManager.setPowerInfo(hpodHeader);
    }

    private void setSustainmentInfo() {
        contentManager.setSustainmentInfo(hpodHeader);
    }

    private void setUnitInfo() {
        contentManager.setUnitInfo(hpodHeader);
    }

    private void setTimersInfo() {
        contentManager.setTimersInfo(hpodHeader);
    }

    private void setupGases() {
        setNitrogen(false);
        setOxygen(false);
        setCarbonDioxyd();
        setArgon();
        sendFloatValuesUpdate();
    }

    private void setNitrogen(boolean updateOthers) {
        nitrogenValue = Util.getRandomBetween(N2MIN, N2MAX);
        String relation = Util.getRelation(nitrogenValue, N2MIN, N2MAX);

        progressValueView1.setTextTop(getString(R.string.chemical_sign_nitrogen));
        progressValueView1.setTextBottom(relation);
        progressValueView1.setProgressValue(nitrogenValue);

        if (updateOthers) {
            setCarbonDioxyd();
            setArgon();
            sendFloatValuesUpdate();
        }
    }

    private void setOxygen(boolean updateOthers) {
        oxygenValue = Util.getRandomBetween(O2MIN, O2MAX);
        String relation = Util.getRelation(oxygenValue, O2MIN, O2MAX);

        progressValueView2.setTextTop(getString(R.string.chemical_sign_oxygen));
        progressValueView2.setTextBottom(relation);
        progressValueView2.setProgressValue(oxygenValue);

        if (updateOthers) {
            setCarbonDioxyd();
            setArgon();
            sendFloatValuesUpdate();
        }
    }

    private void setCarbonDioxyd() {
        carbonDioxydValue = Util.getRandomBetween(CO2MIN, CO2MAX);
    }

    private void setArgon() {
        argonValue = 100f - nitrogenValue - oxygenValue - carbonDioxydValue;
        argonValue = Math.max(argonValue, 0f);
    }

    private void sendFloatValuesUpdate() {
        EventBus.getDefault().post(new Event.FloatValueUpdate(getString(R.string.air_sensor_n2), nitrogenValue));
        EventBus.getDefault().post(new Event.FloatValueUpdate(getString(R.string.air_sensor_o2), oxygenValue));
        EventBus.getDefault().post(new Event.FloatValueUpdate(getString(R.string.air_sensor_ar), argonValue));
        EventBus.getDefault().post(new Event.FloatValueUpdate(getString(R.string.air_sensor_co2), carbonDioxydValue));
    }
}
