package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@EViewGroup(R.layout.view_hpod)
public class HpodView extends LinearLayout {

    @ViewById(R.id.hpodFooter)
    public HpodFooter hpodFooter;

    public HpodView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initViews() {
        // TODO: exchange with start-view
        hpodFooter.setupVitalStatusView();
    }

}
