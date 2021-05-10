package org.pb.android.uxdesign.fragment;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.ui.ViewMode;
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

        progressValueView1.setTextTop("N²");
        progressValueView1.setTextBottom("normal");
        progressValueView1.setProgressValue(Util.getRandomBetween(N2MIN, N2MAX));

        progressValueView2.setTextTop("O²");
        progressValueView2.setTextBottom("normal");
        progressValueView2.setProgressValue(Util.getRandomBetween(O2MIN, O2MAX));
    }

    @Override
    public void onResume() {
        super.onResume();
        hpodFooter.startConsole();
    }

    @Override
    public void onPause() {
        hpodFooter.stopConsole();
        super.onPause();
    }

    @Click(R.id.progressValue1)
    void onProgressValue1Click() {
        progressValueView1.setProgressValue(Util.getRandomBetween(N2MIN, N2MAX));
    }

    @Click(R.id.progressValue2)
    void onProgressValue2Click() {
        progressValueView2.setProgressValue(Util.getRandomBetween(O2MIN, O2MAX));
    }


}
