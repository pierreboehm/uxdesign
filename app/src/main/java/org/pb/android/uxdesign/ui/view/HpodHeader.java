package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.data.user.UserData;

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

    @ViewById(R.id.idBox)
    ViewGroup idBox;        // just for visibility changes

    private CurrentUser currentUser;

    public HpodHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Click(R.id.ivLogo)
    public void onLogoClick() {

    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
        bind(currentUser);
    }

    public void prepareMainScreen() {
        setDischargedState(false);
        idBox.setVisibility(VISIBLE);
    }

    public void prepareVitalStatusScreen() {
        setDischargedState(true);
        idBox.setVisibility(GONE);
    }

    public void prepareSystemStatusScreen() {
        // TODO
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
