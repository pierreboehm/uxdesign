package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.User;

@EViewGroup(R.layout.view_hpod)
public class HpodView extends LinearLayout {

    @ViewById(R.id.hpodHeader)
    HpodHeader hpodHeader;

    @ViewById(R.id.hpodFooter)
    HpodFooter hpodFooter;

    public HpodView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void initViews() {
        // TODO: exchange with start-view
        hpodFooter.setupVitalStatusView();
    }

    public void setupUser(User user) {
        hpodHeader.setUser(user);
    }

}
