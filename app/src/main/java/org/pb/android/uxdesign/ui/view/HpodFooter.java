package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@EViewGroup(R.layout.view_hpod_footer)
public class HpodFooter extends RelativeLayout {

    @ViewById(R.id.viewContainer)
    ViewGroup viewContainer;

    public HpodFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setupVitalStatusView() {
        viewContainer.removeAllViews();

        VitalStatusView vitalStatusView = VitalStatusView_.build(getContext());
        viewContainer.addView(vitalStatusView);
    }

}
