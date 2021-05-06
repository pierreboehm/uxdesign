package org.pb.android.uxdesign.fragment;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

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

    @ViewById(R.id.hpodHeader)
    HpodHeader hpodHeader;

    @ViewById(R.id.hpodFooter)
    HpodFooter hpodFooter;

    @ViewById(R.id.progressValue1)
    HpodProgressValueView progressValueView1;

    @ViewById(R.id.progressValue2)
    HpodProgressValueView progressValueView2;

    @AfterViews
    public void initViews() {
        hpodHeader.prepareScreen(ViewMode.UNIT_INFO);
        hpodFooter.prepareScreen(ViewMode.UNIT_INFO);

        progressValueView1.setTextTop("O²");
        progressValueView1.setTextBottom("normal");
        progressValueView1.setProgressValue(Util.getRandomBetween(0, 100));

        progressValueView2.setTextTop("CO²");
        progressValueView2.setTextBottom("normal");
        progressValueView2.setProgressValue(Util.getRandomBetween(0, 100));
    }

    @Click(R.id.progressValue1)
    void onProgressValue1Click() {
        progressValueView1.setProgressValue(Util.getRandomBetween(0, 100));
    }

    @Click(R.id.progressValue2)
    void onProgressValue2Click() {
        progressValueView2.setProgressValue(Util.getRandomBetween(0, 100));
    }
}
