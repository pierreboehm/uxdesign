package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.data.user.UserData;

@SuppressLint("NonConstantResourceId")
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
    TextView tvState;

    @ViewById(R.id.ivLogo)
    ImageView ivLogo;

    @ViewById(R.id.tvLogo)
    TextView tvLogo;

    @ViewById(R.id.userDataBox)
    ViewGroup userDataBox;

    @ViewById(R.id.idBox)
    ViewGroup idBox;

    @ViewById(R.id.logoBox)
    ViewGroup logoBox;

    private CurrentUser currentUser;

    public HpodHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
        bind(currentUser);
    }

    public void prepareMainScreen() {
        userDataBox.setVisibility(VISIBLE);
        logoBox.setVisibility(VISIBLE);
        idBox.setVisibility(VISIBLE);
        setDischargedState(false, false);
    }

    public void prepareVitalStatusScreen() {
        userDataBox.setVisibility(VISIBLE);
        logoBox.setVisibility(VISIBLE);
        idBox.setVisibility(GONE);
        setDischargedState(true, false);
    }

    public void prepareSystemStatusScreen() {
        userDataBox.setVisibility(GONE);
        logoBox.setVisibility(VISIBLE);
    }

    public void setDischargedState(boolean showDischargedState, boolean involveLogo) {
        if (showDischargedState) {
            tvState.setVisibility(VISIBLE);
            if (involveLogo) {
                ivLogo.clearColorFilter();
                tvLogo.setTextColor(getContext().getColor(R.color.blue_light));
            }
        } else {
            tvState.setVisibility(INVISIBLE);
            if (involveLogo) {
                ivLogo.setColorFilter(getContext().getColor(R.color.blue_cyan));
                tvLogo.setTextColor(getContext().getColor(R.color.blue_cyan));
            }
        }
    }

    public boolean isDischargedState() {
        return tvState.getVisibility() == VISIBLE;
    }

    private void bind(CurrentUser currentUser) {
        UserData userData = currentUser.getUserData();

        tvName.setText(userData.getName());
        tvHome.setText(String.format("%s, %s", userData.getLocality(), userData.getState()));
        tvProfession.setText(userData.getProfession());

        tvIdCode.setText(userData.getId());
    }

}
