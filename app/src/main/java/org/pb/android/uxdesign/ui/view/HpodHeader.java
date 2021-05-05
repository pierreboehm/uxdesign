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
import org.pb.android.uxdesign.ui.ViewMode;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_hpod_header)
public class HpodHeader extends RelativeLayout {

    @ViewById(R.id.headerViewContainer)
    ViewGroup viewContainer;

    @ViewById(R.id.ivLogo)
    ImageView ivLogo;

    @ViewById(R.id.tvLogo)
    TextView tvLogo;

    @ViewById(R.id.logoBox)
    ViewGroup logoBox;

    private UserDataView userDataView;

    private CurrentUser currentUser;
    private ViewMode viewMode;

    public HpodHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
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
        if (userDataView != null) {
            userDataView.setDischargedState(showDischargedState);
        }

        if (showDischargedState) {
            if (involveLogo) {
                ivLogo.clearColorFilter();
                tvLogo.setTextColor(getContext().getColor(R.color.blue_light));
            }
        } else {
            if (involveLogo) {
                ivLogo.setColorFilter(getContext().getColor(R.color.blue_cyan));
                tvLogo.setTextColor(getContext().getColor(R.color.blue_cyan));
            }
        }
    }

    public boolean isDischargedState() {
        return userDataView != null && userDataView.isDischargedState();
    }

    private void prepareMainScreen() {
        viewContainer.removeAllViews();

        userDataView = UserDataView_.build(getContext(), viewMode);
        userDataView.setCurrentUser(currentUser);

        viewContainer.addView(userDataView);

        setDischargedState(false, false);
    }

    private void prepareVitalStatusScreen() {
        viewContainer.removeAllViews();

        userDataView = UserDataView_.build(getContext(), viewMode);
        userDataView.setCurrentUser(currentUser);

        viewContainer.addView(userDataView);

        setDischargedState(true, false);
    }

    private void prepareSystemStatusScreen() {
        viewContainer.removeAllViews();

        userDataView = null;

        setDischargedState(false, true);
    }

}
