package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

import java.util.Locale;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_vital_status)
public class VitalStatusView extends RelativeLayout {

    @ViewById(R.id.vitalGraphView)
    VitalGraphView vitalGraphView;

    @ViewById(R.id.tvBpm)
    TextView tvBpm;

    public VitalStatusView(Context context) {
        super(context);
    }

    public void startVitalGraph() {
        tvBpm.setVisibility(VISIBLE);
        vitalGraphView.start();
    }

    public void stopVitalGraph() {
        vitalGraphView.stop();
        tvBpm.setVisibility(INVISIBLE);
    }

    public void updateBpmValue(int bpmValue) {
        tvBpm.setText(String.format(Locale.getDefault(),"%d/min", bpmValue));
    }
}
