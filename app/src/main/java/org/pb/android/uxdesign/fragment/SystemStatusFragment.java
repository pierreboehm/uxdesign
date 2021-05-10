package org.pb.android.uxdesign.fragment;

import android.annotation.SuppressLint;
import android.util.Pair;

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
import org.pb.android.uxdesign.data.Demonstrator;
import org.pb.android.uxdesign.data.PowerManagerInfo;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.ui.ViewMode;
import org.pb.android.uxdesign.ui.view.ContentItemKeyValueView;
import org.pb.android.uxdesign.ui.view.ContentItemKeyValueView_;
import org.pb.android.uxdesign.ui.view.ContentItemTextView;
import org.pb.android.uxdesign.ui.view.ContentItemTextView_;
import org.pb.android.uxdesign.ui.view.HpodFooter;
import org.pb.android.uxdesign.ui.view.HpodHeader;
import org.pb.android.uxdesign.ui.view.HpodProgressValueView;
import org.pb.android.uxdesign.util.Util;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_system_status)
public class SystemStatusFragment extends Fragment {

    public static final String TAG = SystemStatusFragment.class.getSimpleName();

    private int N2MIN, N2MAX, O2MIN, O2MAX;

    @ViewById(R.id.hpodHeader)
    HpodHeader hpodHeader;

    @ViewById(R.id.hpodFooter)
    HpodFooter hpodFooter;

    @ViewById(R.id.progressValue1)
    HpodProgressValueView progressValueView1;

    @ViewById(R.id.progressValue2)
    HpodProgressValueView progressValueView2;

    @Bean
    Demonstrator demonstrator;

    @AfterInject
    public void afterInject() {
        N2MIN = getResources().getInteger(R.integer.N2MIN);
        N2MAX = getResources().getInteger(R.integer.N2MAX);
        O2MIN = getResources().getInteger(R.integer.O2MIN);
        O2MAX = getResources().getInteger(R.integer.O2MAX);
    }

    @AfterViews
    public void initViews() {
        hpodHeader.prepareScreen(ViewMode.UNIT_INFO);
        hpodFooter.prepareScreen(ViewMode.UNIT_INFO);

        setNitrogen();
        setOxygen();
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
        setNitrogen();
    }

    @Click(R.id.progressValue2)
    void onProgressValue2Click() {
        setOxygen();
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

    private void setApplicationInfo() {
        ContentItemTextView contentItemText = ContentItemTextView_.build(getContext());
        contentItemText.setBoldText(R.string.application_info_headline);
        hpodHeader.setContent(contentItemText);

        contentItemText = ContentItemTextView_.build(getContext());
        contentItemText.setText(R.string.application_info_text_1);
        hpodHeader.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(getContext());
        contentItemText.setText(R.string.application_info_text_2);
        hpodHeader.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(getContext());
        contentItemText.setText(R.string.application_info_text_3);
        hpodHeader.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(getContext());
        contentItemText.setBoldText(R.string.application_info_text_4);
        hpodHeader.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(getContext());
        contentItemText.setText(R.string.application_info_text_5);
        hpodHeader.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(getContext());
        contentItemText.setText(R.string.application_info_text_6);
        hpodHeader.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(getContext());
        contentItemText.setBoldText(R.string.application_info_text_7);
        hpodHeader.addContent(contentItemText);
    }

    private void setUserInfo() {
        ContentItemKeyValueView contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
        contentItemKeyValue.bind(R.string.user_info_key_1, Integer.toString(demonstrator.getUserListCount()));
        hpodHeader.setContent(contentItemKeyValue);

        CurrentUser currentUser = demonstrator.getCurrentUser();

        contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
        contentItemKeyValue.bind(R.string.user_info_key_2, null);
        hpodHeader.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
        contentItemKeyValue.bind(R.string.user_info_key_3, currentUser.getUserData().getName());
        hpodHeader.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
        contentItemKeyValue.bind(R.string.user_info_key_4, currentUser.getUserData().getId());
        hpodHeader.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
        contentItemKeyValue.bind(R.string.user_info_key_5, currentUser.getUserData().getProfession());
        hpodHeader.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
        contentItemKeyValue.bind(R.string.user_info_key_6, currentUser.getUserData().getCountry());
        hpodHeader.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
        contentItemKeyValue.bind(R.string.user_info_key_7, currentUser.getUserData().getLocality());
        hpodHeader.addContent(contentItemKeyValue);
    }

    private void setPowerInfo() {
        PowerManagerInfo powerManagerInfo = demonstrator.getPowerManagerInfo();

        ContentItemKeyValueView contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
        contentItemKeyValue.bind(R.string.power_info_key_1,
                powerManagerInfo.isPluggedIn() ? getString(R.string.yes) : getString(R.string.no));
        hpodHeader.setContent(contentItemKeyValue);

        if (powerManagerInfo.isPluggedIn()) {
            contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
            contentItemKeyValue.bind(R.string.power_info_key_2,
                    powerManagerInfo.getPowerSupplyInVolt() + getString(R.string.unit_voltage));
            hpodHeader.addContent(contentItemKeyValue);

            contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
            contentItemKeyValue.bind(R.string.power_info_key_3,
                    powerManagerInfo.isLoading() ? getString(R.string.yes) : getString(R.string.no));
            hpodHeader.addContent(contentItemKeyValue);

            if (powerManagerInfo.isLoading()) {
                contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
                contentItemKeyValue.bind(R.string.power_info_key_4,
                        powerManagerInfo.getLoadingStateInPercent() + getString(R.string.unit_percent));
                hpodHeader.addContent(contentItemKeyValue);
            }
        }

        contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
        contentItemKeyValue.bind(R.string.power_info_key_5,
                powerManagerInfo.getCurrentConsumptionInMilliAmpere() + getString(R.string.unit_current_milli));
        hpodHeader.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(getContext());
        contentItemKeyValue.bind(R.string.power_info_key_6,
                powerManagerInfo.getTemperatureInCelsius() + getString(R.string.unit_temperature));
        hpodHeader.addContent(contentItemKeyValue);
    }

    private void setNitrogen() {
        Pair<Integer, String> nitrogenValues = getProgressValueAndRelation(N2MIN, N2MAX);
        progressValueView1.setTextTop(getString(R.string.chemical_sign_nitrogen));
        progressValueView1.setTextBottom(nitrogenValues.second);
        progressValueView1.setProgressValue(nitrogenValues.first);
    }

    private void setOxygen() {
        Pair<Integer, String> oxygenValues = getProgressValueAndRelation(O2MIN, O2MAX);
        progressValueView2.setTextTop(getString(R.string.chemical_sign_oxygen));
        progressValueView2.setTextBottom(oxygenValues.second);
        progressValueView2.setProgressValue(oxygenValues.first);
    }

    private Pair<Integer, String> getProgressValueAndRelation(int min, int max) {
        int random = Util.getRandomBetween(min, max);
        String relation = Util.getRelation((float) random, (float) min, (float) max);
        return new Pair<>(random, relation);
    }

}
