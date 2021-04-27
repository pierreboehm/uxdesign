package org.pb.android.uxdesign.fragment;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.Demonstrator;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.ui.view.HpodFooter;
import org.pb.android.uxdesign.ui.view.HpodHeader;

@SuppressLint("NonConstantResourceId")
@EFragment(R.layout.fragment_vital_status)
public class VitalStatusFragment extends Fragment {

    public static final String TAG = VitalStatusFragment.class.getSimpleName();

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

        hpodHeader.prepareVitalStatusScreen();
        hpodFooter.prepareVitalStatusScreen();
    }

    @Click(R.id.ivLogo)
    public void onLogoClick() {
        if (hpodHeader.isDischargedState()) {
            hpodHeader.setDischargedState(false, true);
            hpodFooter.startVitalGraph();
        } else {
            hpodHeader.setDischargedState(true, true);
            hpodFooter.stopVitalGraph();
        }
    }
}
