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
import org.pb.android.uxdesign.ui.ViewMode;

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

    @ViewById(R.id.modeBox)
    ViewGroup modeBox;

    @ViewById(R.id.logoBox)
    ViewGroup logoBox;

    @ViewById(R.id.tvMainText)
    TextView tvMainText;

    @ViewById(R.id.tvSubText)
    TextView tvSubText;

    private CurrentUser currentUser;
    private ViewMode viewMode;

    public HpodHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
        bind(currentUser);
    }

    public void prepareScreen(ViewMode viewMode) {
        this.viewMode = viewMode;

        switch (viewMode) {
            case MAIN: {
                prepareMainScreen();
                break;
            }
            case PASSENGER_INFO: {
                prepareVitalStatusScreen();
                break;
            }
            case UNIT_INFO: {
                prepareSystemStatusScreen();
                break;
            }
            case MAINTENANCE: {
                break;
            }
        }
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

    private void prepareMainScreen() {
        userDataBox.setVisibility(VISIBLE);
        logoBox.setVisibility(VISIBLE);
        idBox.setVisibility(VISIBLE);
        modeBox.setVisibility(VISIBLE);
        tvSubText.setVisibility(GONE);
        tvMainText.setText("HPOD SYSTEMS OPTIMAL");
        setDischargedState(false, false);
    }

    private void prepareVitalStatusScreen() {
        userDataBox.setVisibility(VISIBLE);
        logoBox.setVisibility(VISIBLE);
        idBox.setVisibility(GONE);
        modeBox.setVisibility(VISIBLE);
        tvSubText.setVisibility(VISIBLE);
        tvMainText.setText("HPOD SYSTEM");
        tvSubText.setText("PASSENGER VITAL STATUS");
        setDischargedState(true, false);
    }

    private void prepareSystemStatusScreen() {
        userDataBox.setVisibility(GONE);
        logoBox.setVisibility(VISIBLE);
        idBox.setVisibility(GONE);
        modeBox.setVisibility(GONE);
        setDischargedState(false, true);
    }

    private void bind(CurrentUser currentUser) {
        UserData userData = currentUser.getUserData();

        String locality = userData.getLocality();
        String state = userData.getState();
        String country = userData.getCountry();

        tvName.setText(userData.getName());
        tvHome.setText(String.format("%s, %s", locality, locality.equals(state) ? country : state));
        tvProfession.setText(userData.getProfession());

        tvIdCode.setText(userData.getId());
    }

}
