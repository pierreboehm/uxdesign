package org.pb.android.uxdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.greenrobot.eventbus.EventBus;
import org.pb.android.uxdesign.fragment.MainFragment;
import org.pb.android.uxdesign.fragment.MainFragment_;
import org.pb.android.uxdesign.fragment.SystemStatusFragment;
import org.pb.android.uxdesign.fragment.SystemStatusFragment_;
import org.pb.android.uxdesign.fragment.VitalStatusFragment;
import org.pb.android.uxdesign.fragment.VitalStatusFragment_;

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
        // TODO: uncomment
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        // TODO: uncomment
        //EventBus.getDefault().unregister(this);
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