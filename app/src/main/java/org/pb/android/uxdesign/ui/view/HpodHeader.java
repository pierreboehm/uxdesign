package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.data.user.UserData;
import org.pb.android.uxdesign.event.Event;

@EViewGroup(R.layout.view_hpod_header)
public class HpodHeader extends RelativeLayout {

    @ViewById(R.id.tvName)
    TextView tvName;

    @ViewById(R.id.tvHome)
    TextView tvHome;

    @ViewById(R.id.tvProfession)
    TextView tvProfession;

    @ViewById(R.id.tvIdCode)
    TextView tvIdCode;

    @ViewById(R.id.tvState)
    TextView tvState;       // just for visibility changes

    @ViewById(R.id.userDataBox)
    ViewGroup userDataBox;

    @ViewById(R.id.idBox)
    ViewGroup idBox;        // just for visibility changes

    @ViewById(R.id.logoBox)
    ViewGroup logoBox;

    private CurrentUser currentUser;

    public HpodHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Click(R.id.ivLogo)
    public void onLogoClick() {
        EventBus.getDefault().post(new Event.ShowUserStatus());
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
        bind(currentUser);
    }

    public void prepareMainScreen() {
        userDataBox.setVisibility(VISIBLE);
        logoBox.setVisibility(VISIBLE);
        idBox.setVisibility(VISIBLE);
        setDischargedState(false);
    }

    public void prepareVitalStatusScreen() {
        userDataBox.setVisibility(VISIBLE);
        logoBox.setVisibility(VISIBLE);
        idBox.setVisibility(GONE);
        setDischargedState(true);
    }

    public void prepareSystemStatusScreen() {
        userDataBox.setVisibility(GONE);
        logoBox.setVisibility(VISIBLE);
    }

    public void setDischargedState(boolean showDischargedState) {
        if (showDischargedState) {
            tvState.setVisibility(VISIBLE);
        } else {
            tvState.setVisibility(INVISIBLE);
        }
    }

    private void bind(CurrentUser currentUser) {
        UserData userData = currentUser.getUserData();

        tvName.setText(userData.getName());
        tvHome.setText(String.format("%s, %s", userData.getLocality(), userData.getState()));
        tvProfession.setText(userData.getProfession());

        tvIdCode.setText(userData.getId());
    }

}
