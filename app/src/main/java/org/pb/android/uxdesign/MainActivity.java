package org.pb.android.uxdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.BatteryManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.UiThread;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.pb.android.uxdesign.data.Demonstrator;
import org.pb.android.uxdesign.event.Event;
import org.pb.android.uxdesign.fragment.MainFragment;
import org.pb.android.uxdesign.fragment.MainFragment_;
import org.pb.android.uxdesign.fragment.SystemStatusFragment;
import org.pb.android.uxdesign.fragment.SystemStatusFragment_;
import org.pb.android.uxdesign.fragment.VitalStatusFragment;
import org.pb.android.uxdesign.fragment.VitalStatusFragment_;
import org.pb.android.uxdesign.ui.ViewMode;
import org.pb.android.uxdesign.ui.dialog.ContentDialog;
import org.pb.android.uxdesign.ui.dialog.WarningDialog;
import org.pb.android.uxdesign.ui.view.ContentItemContainerView;
import org.pb.android.uxdesign.ui.view.ContentItemContainerView_;
import org.pb.android.uxdesign.ui.view.ContentItemKeyValueView;
import org.pb.android.uxdesign.ui.view.ContentItemKeyValueView_;
import org.pb.android.uxdesign.ui.view.ContentItemTextView;
import org.pb.android.uxdesign.ui.view.ContentItemTextView_;
import org.pb.android.uxdesign.ui.view.ContentItemWarningView;
import org.pb.android.uxdesign.ui.view.ContentItemWarningView_;
import org.pb.android.uxdesign.ui.view.UserListView;
import org.pb.android.uxdesign.ui.view.UserListView_;

@SuppressLint("NonConstantResourceId")
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Bean
    Demonstrator demonstrator;

    private WarningDialog warningDialog;
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

    @Receiver(actions = {Intent.ACTION_BATTERY_CHANGED, Intent.ACTION_BATTERY_LOW, Intent.ACTION_BATTERY_OKAY})
    protected void onBatteryChanged(Intent batteryStatus) {
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING)
                || (status == BatteryManager.BATTERY_STATUS_FULL);

        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_USB);
        boolean acCharge = (chargePlug == BatteryManager.BATTERY_PLUGGED_AC);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryLevelInPercent = level * 100 / (float) scale;

        demonstrator.setChargingState(isCharging);
        demonstrator.setPluggedState(usbCharge || acCharge);
        demonstrator.setChargingLevel(batteryLevelInPercent);

        if (batteryStatus.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            showDialog(ViewMode.WARNING);

        } else if (batteryStatus.getAction().equals(Intent.ACTION_BATTERY_OKAY)) {
            if (warningDialog != null) {
                warningDialog.dismiss();
                warningDialog = null;
            }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ReportError event) {
        demonstrator.reportError();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ReportFailure event) {
        demonstrator.reportFailure();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ReportTimerStarted event) {
        demonstrator.reportTimerStarted();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.ReportTimerStopped event) {
        demonstrator.reportTimerStopped();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.DrawingCacheBitmapUpdate event) {
        performDrawingCacheBitmapUpdate(event.getUpdateData());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.FloatValueUpdate event) {
        performFloatValueUpdate(event.getUpdateData());
    }

    private void showDialog(ViewMode viewMode) {
        View dialogContent = getDialogContent(viewMode);
        if (dialogContent == null) {
            return;
        }

        if (viewMode == ViewMode.WARNING) {
            if (warningDialog != null) {
                warningDialog.dismiss();
            }

            warningDialog = new WarningDialog.Builder(this)
                    .setContent(dialogContent)
                    .build();
            warningDialog.show();

        } else {
            new ContentDialog.Builder(this)
                    .setContent(dialogContent)
                    .setHeaderByViewMode(viewMode)
                    .build()
                    .show();
        }
    }

    private View getDialogContent(ViewMode viewMode) {

        switch (viewMode) {
            case MAIN: {
                ContentItemContainerView containerView = ContentItemContainerView_.build(this);

                ContentItemKeyValueView contentItemKeyValue = ContentItemKeyValueView_.build(this);
                contentItemKeyValue.setTitle("SECURITY INFORMATION", true);
                containerView.setView(contentItemKeyValue);

                contentItemKeyValue = ContentItemKeyValueView_.build(this);
                contentItemKeyValue.bind("Count of security breaches:", "0");
                containerView.addView(contentItemKeyValue);

                contentItemKeyValue = ContentItemKeyValueView_.build(this);
                contentItemKeyValue.bind("Use this code for verification issues.", null);
                containerView.addView(contentItemKeyValue);

                ContentItemTextView contentItemText = ContentItemTextView_.build(this);
                containerView.addView(contentItemText);

                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(demonstrator.getAccessCodeBitmap());
                containerView.addView(imageView);

                contentItemText = ContentItemTextView_.build(this);
                containerView.addView(contentItemText);

                contentItemText = ContentItemTextView_.build(this);
                contentItemText.setText(getString(R.string.application_info_text_4));
                containerView.addView(contentItemText);

                return containerView;
            }
            case PASSENGER_INFO: {
                UserListView userListView = UserListView_.build(this);
                return userListView;
            }
            case UNIT_INFO: {
                // TODO: implement
                break;
            }
            case WARNING: {
                ContentItemWarningView contentItemWarningView = ContentItemWarningView_.build(this);
                // TODO: modify content here
                return contentItemWarningView;
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

    private void performDrawingCacheBitmapUpdate(Pair<String, Bitmap> updateData) {
        String key = updateData.first;
        Bitmap bitmap = updateData.second;

        if (key.equals(getString(R.string.air_sensor_n2))) {
            demonstrator.setN2StatusBitmap(bitmap);
        } else if (key.equals(getString(R.string.air_sensor_o2))) {
            demonstrator.setO2StatusBitmap(bitmap);
        } else if (key.equals(getString(R.string.air_sensor_ar))) {
            demonstrator.setArStatusBitmap(bitmap);
        } else if (key.equals(getString(R.string.air_sensor_co2))) {
            demonstrator.setCo2StatusBitmap(bitmap);
        }
    }

    private void performFloatValueUpdate(Pair<String, Float> updateData) {
        String key = updateData.first;
        float value = updateData.second;

        if (key.equals(getString(R.string.air_sensor_n2))) {
            demonstrator.setN2Level(value);
        } else if (key.equals(getString(R.string.air_sensor_o2))) {
            demonstrator.setO2level(value);
        } else if (key.equals(getString(R.string.air_sensor_ar))) {
            demonstrator.setArLevel(value);
        } else if (key.equals(getString(R.string.air_sensor_co2))) {
            demonstrator.setCo2Level(value);
        }
    }
}