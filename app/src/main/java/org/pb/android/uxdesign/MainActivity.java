package org.pb.android.uxdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.fragment.MainFragment;
import org.pb.android.uxdesign.fragment.MainFragment_;
import org.pb.android.uxdesign.fragment.SystemStatusFragment;
import org.pb.android.uxdesign.fragment.SystemStatusFragment_;
import org.pb.android.uxdesign.fragment.VitalStatusFragment;
import org.pb.android.uxdesign.fragment.VitalStatusFragment_;
import org.pb.android.uxdesign.ui.ViewMode;
import org.pb.android.uxdesign.ui.dialog.ContentDialog;
import org.pb.android.uxdesign.ui.view.UserListView;
import org.pb.android.uxdesign.ui.view.UserListView_;

@SuppressLint("NonConstantResourceId")
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Toast closeAppToast;

    @AfterViews
    public void initViews() {
        startMainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (closeAppToast != null) {
            closeAppToast.cancel();
            Log.d(TAG, "Activity termination requested.");
            finish();
        } else {
            closeAppToast = Toast.makeText(this, R.string.backPressedHintText, Toast.LENGTH_SHORT);
            closeAppToast.show();
            resetCloseAppToast();
        }
    }

    @UiThread(delay = 2000)
    void resetCloseAppToast() {
        closeAppToast = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ShowUserStatus event) {
        startMainFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ShowVitalStatus event) {
        startVitalStatusFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ShowSystemStatus event) {
        startSystemStatusFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ShowDialog event) {
        showDialog(event.getViewMode());
    }

    private void showDialog(ViewMode viewMode) {
        ViewGroup dialogContent = getDialogContent(viewMode);
        if (dialogContent == null) {
            return;
        }

        new ContentDialog.Builder(this)
                .setContent(dialogContent)
                .setHeaderByViewMode(viewMode)
                .build()
                .show();
    }

    private ViewGroup getDialogContent(ViewMode viewMode) {

        switch (viewMode) {
            case MAIN: {
                // TODO: set dialog content
                break;
            }
            case PASSENGER_INFO: {
                UserListView userListView = UserListView_.build(this);
                return userListView;
            }
            case UNIT_INFO: {
                // TODO: implement
                break;
            }
        }

        return null;
    }

    private void startMainFragment() {
        MainFragment mainFragment = MainFragment_.builder().build();
        setFragment(mainFragment, MainFragment.TAG);
    }

    private void startVitalStatusFragment() {
        VitalStatusFragment vitalStatusFragment = VitalStatusFragment_.builder().build();
        setFragment(vitalStatusFragment, VitalStatusFragment.TAG);
    }

    private void startSystemStatusFragment() {
        SystemStatusFragment systemStatusFragment = SystemStatusFragment_.builder().build();
        setFragment(systemStatusFragment, SystemStatusFragment.TAG);
    }

    private void setFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment, fragmentTag)
                .commit();
    }
}