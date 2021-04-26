package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.EViewGroup;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.User;

@EViewGroup(R.layout.view_hpod_header)
public class HpodHeader extends RelativeLayout {

    private User user;

    public HpodHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setUser(User user) {
        this.user = user;
        bind(user);
    }

    private void bind(User user) {

    }

}
