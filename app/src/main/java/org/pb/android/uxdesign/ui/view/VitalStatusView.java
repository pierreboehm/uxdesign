package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_vital_status)
public class VitalStatusView extends LinearLayout {

    @ViewById(R.id.vitalGraphView)
    VitalGraphView vitalGraphView;

    public VitalStatusView(Context context) {
        super(context);
    }

    public void startVitalGraph() {
        vitalGraphView.start();
    }

    public void stopVitalGraph() {
        vitalGraphView.stop();
    }
}
