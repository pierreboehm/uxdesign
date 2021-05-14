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
import org.pb.android.uxdesign.ui.view.HpodStatusOverviewView;
import org.pb.android.uxdesign.util.Util;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_system_status)
public class SystemStatusFragment extends Fragment {

    public static final String TAG = SystemStatusFragment.class.getSimpleName();

    private enum UnitInfoContentType {
        UNDEFINED, APPLICATION_INFO, USER_INFO, POWER_INFO, SUSTAINMENT_INFO, UNIT_INFO, TIMERS_INFO
    }

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

    @ViewById(R.id.statusOverview)
    HpodStatusOverviewView statusOverview;

    @Bean
    ContentManager contentManager;

    private UnitInfoContentType unitInfoContentType = UnitInfoContentType.UNDEFINED;
    private boolean initiated;

    @AfterInject
    public void afterInject() {
        N2MIN = ResourcesCompat.getFloat(getResources(), R.dimen.n2_min);
        N2MAX = ResourcesCompat.getFloat(getResources(), R.dimen.n2_max);

        O2MIN = ResourcesCompat.getFloat(getResources(), R.dimen.o2_min);
        O2MAX = ResourcesCompat.getFloat(getResources(), R.dimen.o2_max);

        CO2MIN = ResourcesCompat.getFloat(getResources(), R.dimen.co2_min);
        CO2MAX = ResourcesCompat.getFloat(getResources(), R.dimen.co2_max);

        nitrogenValue = Util.getRandomBetween(N2MIN, N2MIN + (N2MIN * .25f));
        oxygenValue = Util.getRandomBetween(O2MIN, O2MIN + (O2MIN * .25f));
        carbonDioxydValue = Util.getRandomBetween(CO2MIN, CO2MIN + (CO2MIN * .25f));
    }

    @AfterViews
    public void initViews() {
        hpodHeader.prepareScreen(ViewMode.UNIT_INFO);
        hpodFooter.prepareScreen(ViewMode.UNIT_INFO);

        setupGases();
        initiated = true;
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
    public void onEvent(Event.Refresh event) {
        if (initiated) {
            int gasUpdate = Util.getRandomBetween(1, 5);
            if (gasUpdate == 1) {
                setNitrogen(true);
            } else if (gasUpdate == 5) {
                setOxygen(true);
            }

            statusOverview.refresh();
            hpodFooter.refresh();
            contentRefresh(unitInfoContentType);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ContentClear event) {
        contentRefresh(UnitInfoContentType.UNDEFINED);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Event.ContentShowApplicationInfo event) {
        EventBus.getDefault().removeStickyEvent(event);
        contentRefresh(UnitInfoContentType.APPLICATION_INFO);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Event.ContentShowUserInfo event) {
        EventBus.getDefault().removeStickyEvent(event);
        contentRefresh(UnitInfoContentType.USER_INFO);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Event.ContentShowPowerInfo event) {
        EventBus.getDefault().removeStickyEvent(event);
        contentRefresh(UnitInfoContentType.POWER_INFO);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Event.ContentShowSustainmentInfo event) {
        EventBus.getDefault().removeStickyEvent(event);
        contentRefresh(UnitInfoContentType.SUSTAINMENT_INFO);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Event.ContentShowUnitInfo event) {
        EventBus.getDefault().removeStickyEvent(event);
        contentRefresh(UnitInfoContentType.UNIT_INFO);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Event.ContentShowTimersInfo event) {
        EventBus.getDefault().removeStickyEvent(event);
        contentRefresh(UnitInfoContentType.TIMERS_INFO);
    }

    private void setupGases() {
        setNitrogen(false);
        setOxygen(false);
        setCarbonDioxyd();
        setArgon();
        sendFloatValuesUpdate();
    }

    private void setNitrogen(boolean updateOthers) {
        nitrogenValue = Util.getGasLevel(nitrogenValue, N2MIN, N2MAX);
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
        oxygenValue = Util.getGasLevel(oxygenValue, O2MIN, O2MAX);
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
        carbonDioxydValue = Util.getGasLevel(carbonDioxydValue, CO2MIN, CO2MAX);
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

    private void contentRefresh(UnitInfoContentType contentType) {
        unitInfoContentType = contentType;

        switch (contentType) {
            case APPLICATION_INFO: {
                contentManager.setApplicationInfo(hpodHeader);
                break;
            }
            case USER_INFO: {
                contentManager.setUserInfo(hpodHeader);
                break;
            }
            case POWER_INFO: {
                contentManager.setPowerInfo(hpodHeader);
                break;
            }
            case SUSTAINMENT_INFO: {
                contentManager.setSustainmentInfo(hpodHeader);
                break;
            }
            case UNIT_INFO: {
                contentManager.setUnitInfo(hpodHeader);
                break;
            }
            case TIMERS_INFO: {
                contentManager.setTimersInfo(hpodHeader);
                break;
            }
            case UNDEFINED: {
                hpodHeader.clearContent();
                break;
            }
        }
    }
}
