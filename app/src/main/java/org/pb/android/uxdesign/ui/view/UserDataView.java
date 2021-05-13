package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.data.user.UserData;
import org.pb.android.uxdesign.ui.ViewMode;

@SuppressLint({"NonConstantResourceId", "ViewConstructor"})
@EViewGroup(R.layout.view_user_data)
public class UserDataView extends LinearLayout {

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

    @ViewById(R.id.tvMainText)
    TextView tvMainText;

    @ViewById(R.id.tvSubText)
    TextView tvSubText;

    @ViewById(R.id.idBox)
    ViewGroup idBox;

    private ViewMode viewMode;

    public UserDataView(Context context, ViewMode viewMode) {
        super(context);
        this.viewMode = viewMode;
    }

    @AfterViews
    public void initView() {
        prepareScreen();
    }

    public void setCurrentUser(CurrentUser currentUser) {
        bind(currentUser);
    }

    public void setDischargedState(boolean showDischargedState) {
        if (showDischargedState) {
            tvState.setVisibility(VISIBLE);
            idBox.setVisibility(INVISIBLE);
        } else {
            tvState.setVisibility(INVISIBLE);
            idBox.setVisibility(VISIBLE);
        }
    }

    public boolean isDischargedState() {
        return tvState.getVisibility() == VISIBLE;
    }

    private void prepareScreen() {
        if (viewMode == ViewMode.MAIN) {
            tvSubText.setVisibility(GONE);
            tvMainText.setText(R.string.hpod_systems_optimal);

        } else if (viewMode == ViewMode.PASSENGER_INFO) {
            tvSubText.setVisibility(VISIBLE);
            tvMainText.setText(R.string.hpod_system);
            tvSubText.setText(R.string.passenger_vital_status);
        }
    }

    private void bind(CurrentUser currentUser) {
        UserData userData = currentUser.getUserData();

        tvName.setText(userData.getName());
        tvHome.setText(String.format("%s, %s", userData.getLocality(), userData.getCountry()));
        tvProfession.setText(userData.getProfession());

        tvIdCode.setText(userData.getId());
    }
}
