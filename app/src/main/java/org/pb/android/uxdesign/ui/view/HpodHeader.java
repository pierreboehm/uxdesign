package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.ui.ViewMode;
import org.pb.android.uxdesign.ui.button.LogoButtonView;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_hpod_header)
public class HpodHeader extends RelativeLayout {

    @ViewById(R.id.headerViewContainer)
    ViewGroup viewContainer;

    @ViewById(R.id.ib_Leave)
    ImageButton leaveButton;

    @ViewById(R.id.logoButtonView)
    LogoButtonView logoButtonView;

    @ViewById(R.id.unitInfoTopMenueView)
    UnitInfoTopMenuView unitInfoTopMenu;

    private UnitInfoTopContentView unitInfoTopContent;
    private UserDataView userDataView;

    private CurrentUser currentUser;
    private ViewMode viewMode;

    public HpodHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
        updateCurrentUser();
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

    public void setDischargedState(boolean showDischargedState) {
        if (userDataView != null) {
            userDataView.setDischargedState(showDischargedState);
        }
    }

    public void setLogoButtonSelected(boolean selected) {
        logoButtonView.setSelected(selected);

        if (logoButtonView.hasWaitingState()) {
            leaveButton.setVisibility(selected ? GONE : VISIBLE);
            setDischargedState(!selected);
        }
    }

    public boolean isLogoButtonSelected() {
        return logoButtonView.isSelected();
    }

    public void clearContent() {
        if (unitInfoTopContent != null) {
            unitInfoTopContent.clearContent();
        }
    }

    public void setContent(View view) {
        if (unitInfoTopContent != null) {
            unitInfoTopContent.setContentView(view);
        }
    }

    public void addContent(View view) {
        if (unitInfoTopContent != null) {
            unitInfoTopContent.addContentView(view);
        }
    }

    private void prepareMainScreen() {
        viewContainer.removeAllViews();

        leaveButton.setVisibility(GONE);
        logoButtonView.setWaitingState(false);
        logoButtonView.setVisibility(VISIBLE);
        unitInfoTopMenu.setVisibility(GONE);

        unitInfoTopContent = null;

        userDataView = UserDataView_.build(getContext(), viewMode);
        userDataView.setCurrentUser(currentUser);

        viewContainer.addView(userDataView);

        setDischargedState(false);
        setLogoButtonSelected(false);
    }

    private void prepareVitalStatusScreen() {
        viewContainer.removeAllViews();

        leaveButton.setVisibility(VISIBLE);
        logoButtonView.setWaitingState(true);
        logoButtonView.setVisibility(VISIBLE);
        unitInfoTopMenu.setVisibility(GONE);

        unitInfoTopContent = null;

        userDataView = UserDataView_.build(getContext(), viewMode);
        userDataView.setCurrentUser(currentUser);

        viewContainer.addView(userDataView);

        setDischargedState(true);
        setLogoButtonSelected(false);
    }

    private void prepareSystemStatusScreen() {
        viewContainer.removeAllViews();

        leaveButton.setVisibility(GONE);
        logoButtonView.setWaitingState(false);
        logoButtonView.setVisibility(GONE);
        unitInfoTopMenu.setVisibility(VISIBLE);

        userDataView = null;

        unitInfoTopContent = UnitInfoTopContentView_.build(getContext());
        viewContainer.addView(unitInfoTopContent);

        setDischargedState(false);
        setLogoButtonSelected(false);
    }

    private void updateCurrentUser() {
        if (userDataView != null) {
            userDataView.setCurrentUser(currentUser);
        }
    }

}
