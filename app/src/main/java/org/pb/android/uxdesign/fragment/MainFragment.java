package org.pb.android.uxdesign.fragment;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.data.Demonstrator;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
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

    @AfterViews
    public void initViews() {
        CurrentUser currentUser = demonstrator.getCurrentUser();
        hpodHeader.setCurrentUser(currentUser);

        hpodHeader.prepareScreen(ViewMode.MAIN);
        hpodFooter.prepareScreen(ViewMode.MAIN);

        powerSystemStatusView.preparePowerSystemStatusScreen();
        cpuSystemStatusView.prepareCpuSystemStatusScreen();
        tempSystemStatusView.prepareTempSystemStatusScreen();
    }

}
