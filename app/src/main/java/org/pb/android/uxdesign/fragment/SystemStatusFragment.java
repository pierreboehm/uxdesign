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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ContentShowUserInfo event) {
        setUserInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ContentShowPowerInfo event) {
        setPowerInfo();
    }

    private void setUserInfo() {
        ContentItemTextView contentItemTextView = ContentItemTextView_.build(getContext());
        contentItemTextView.bind("Count of registered users:", Integer.toString(demonstrator.getUserListCount()));
        hpodHeader.setContent(contentItemTextView);

        CurrentUser currentUser = demonstrator.getCurrentUser();

        contentItemTextView = ContentItemTextView_.build(getContext());
        contentItemTextView.bind("Currently selected user", null);
        hpodHeader.addContent(contentItemTextView);

        contentItemTextView = ContentItemTextView_.build(getContext());
        contentItemTextView.bind("Name:", currentUser.getUserData().getName());
        hpodHeader.addContent(contentItemTextView);

        contentItemTextView = ContentItemTextView_.build(getContext());
        contentItemTextView.bind("Id:", currentUser.getUserData().getId());
        hpodHeader.addContent(contentItemTextView);

        contentItemTextView = ContentItemTextView_.build(getContext());
        contentItemTextView.bind("Profession:", currentUser.getUserData().getProfession());
        hpodHeader.addContent(contentItemTextView);

        contentItemTextView = ContentItemTextView_.build(getContext());
        contentItemTextView.bind("Country:", currentUser.getUserData().getCountry());
        hpodHeader.addContent(contentItemTextView);

        contentItemTextView = ContentItemTextView_.build(getContext());
        contentItemTextView.bind("Locality:", currentUser.getUserData().getLocality());
        hpodHeader.addContent(contentItemTextView);
    }

    private void setPowerInfo() {
        PowerManagerInfo powerManagerInfo = demonstrator.getPowerManagerInfo();

        ContentItemTextView contentItemTextView = ContentItemTextView_.build(getContext());
        contentItemTextView.bind("External power supply:", powerManagerInfo.isPluggedIn() ? "YES" : "NO");
        hpodHeader.setContent(contentItemTextView);

        if (powerManagerInfo.isPluggedIn()) {
            contentItemTextView = ContentItemTextView_.build(getContext());
            contentItemTextView.bind("Voltage:", powerManagerInfo.getPowerSupplyInVolt() + "V");
            hpodHeader.addContent(contentItemTextView);

            contentItemTextView = ContentItemTextView_.build(getContext());
            contentItemTextView.bind("Is loading:", powerManagerInfo.isLoading() ? "YES" : "NO");
            hpodHeader.addContent(contentItemTextView);

            if (powerManagerInfo.isLoading()) {
                contentItemTextView = ContentItemTextView_.build(getContext());
                contentItemTextView.bind("Loading status:", powerManagerInfo.getLoadingStateInPercent() + "%");
                hpodHeader.addContent(contentItemTextView);
            }
        }

        contentItemTextView = ContentItemTextView_.build(getContext());
        contentItemTextView.bind("Current consumption:", powerManagerInfo.getCurrentConsumptionInMilliAmpere() + "mA");
        hpodHeader.addContent(contentItemTextView);

        contentItemTextView = ContentItemTextView_.build(getContext());
        contentItemTextView.bind("Battery temperature:", powerManagerInfo.getTemperatureInCelsius() + "°C");
        hpodHeader.addContent(contentItemTextView);
    }

    private void setNitrogen() {
        Pair<Integer, String> nitrogenValues = getProgressValueAndRelation(N2MIN, N2MAX);
        progressValueView1.setTextTop("N²");
        progressValueView1.setTextBottom(nitrogenValues.second);
        progressValueView1.setProgressValue(nitrogenValues.first);
    }

    private void setOxygen() {
        Pair<Integer, String> oxygenValues = getProgressValueAndRelation(O2MIN, O2MAX);
        progressValueView2.setTextTop("O²");
        progressValueView2.setTextBottom(oxygenValues.second);
        progressValueView2.setProgressValue(oxygenValues.first);
    }

    private Pair<Integer, String> getProgressValueAndRelation(int min, int max) {
        int random = Util.getRandomBetween(min, max);
        String relation = Util.getRelation((float) random, (float) min, (float) max);
        return new Pair<>(random, relation);
    }

}
