package org.pb.android.uxdesign.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.pb.android.uxdesign.AppPreferences_;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.data.user.User;
import org.pb.android.uxdesign.data.user.UserData;
import org.pb.android.uxdesign.data.user.Users;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@EBean(scope = EBean.Scope.Singleton)
public class Demonstrator {

    private static final String TAG = Demonstrator.class.getSimpleName();

    @RootContext
    Context context;

    @Pref
    AppPreferences_ preferences;

    private PowerManagerInfo powerManagerInfo;
    private Bitmap accessCodeBitmap;

    private long timestampStart;
    private int errors = 0;
    private int failures = 0;
    private int timers = 0;

    @AfterInject
    public void afterInject() {
        powerManagerInfo = new PowerManagerInfo();
        timestampStart = System.currentTimeMillis();
    }

    public void reportError() {
        errors++;
    }

    public int getErrors() {
        return errors;
    }

    public void reportFailure() {
        failures++;
    }

    public int getFailures() {
        return failures;
    }

    public void reportTimerStarted() {
        timers++;
    }

    public void reportTimerStopped() {
        timers--;
    }

    public int getTimersRunning() {
        return timers;
    }


    @SuppressLint("DefaultLocale")
    public String getRuntime() {
        long timestamp = System.currentTimeMillis();

        Date date1 = new Date(timestampStart);
        Date date2 = new Date(timestamp);

        long differenceInMilliSeconds = Math.abs(date2.getTime() - date1.getTime());
        long differenceInDays = (differenceInMilliSeconds / (24 * 60 * 60 * 1000)) % 365;
        long differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;
        long differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;
        long differenceInSeconds = (differenceInMilliSeconds / 1000) % 60;

        if (differenceInDays > 0) {
            return String.format("%dd %02d:%02d:%02d", differenceInDays, differenceInHours, differenceInMinutes, differenceInSeconds);
        } else {
            return String.format("%02d:%02d:%02d", differenceInHours, differenceInMinutes, differenceInSeconds);
        }

    }

    public void setAccessCodeBitmap(Bitmap bitmap) {
        accessCodeBitmap = bitmap;
    }

    public Bitmap getAccessCodeBitmap() {
        return accessCodeBitmap;
    }

    public CurrentUser getCurrentUser() {
        UserData userData = loadUserData();
        return new CurrentUser(userData);
    }

    public List<UserData> getUserDataList() {
        List<User> userList = getUserList();
        List<UserData> resultList = new ArrayList<>();

        for (User user : userList) {
            UserData userData = UserData.create().setData(user).getUserData();
            resultList.add(userData);
        }

        return resultList;
    }

    public int getUserListCount() {
        return getUserList().size();
    }

    public PowerManagerInfo getPowerManagerInfo() {
        return powerManagerInfo;
    }

    public void setPluggedState(boolean isPlugged) {
        powerManagerInfo.setPlugged(isPlugged);
    }

    public void setChargingState(boolean isCharging) {
        powerManagerInfo.setCharging(isCharging);
    }

    public void setChargingLevel(float chargingLevel) {
        powerManagerInfo.setBatteryChargingLevelInPercent(chargingLevel);
    }

    private List<User> getUserList() {
        List<User> resultList = new ArrayList<>();

        Serializer serializer = new Persister();
        InputStream xmlUsers = context.getResources().openRawResource(R.raw.users);

        try {
            Users users = serializer.read(Users.class, xmlUsers);
            if (users != null) {
                resultList = users.getUserList();
            }
        } catch (Exception exception) {
            Log.e(TAG, Objects.requireNonNull(exception.getLocalizedMessage()));
        } finally {
            try {
                xmlUsers.close();
            } catch (Exception exception) {
                // not implemented
            }
        }

        return resultList;
    }

    private UserData loadUserData() {
        User user = new User();

        Serializer serializer = new Persister();
        InputStream xmlUsers = context.getResources().openRawResource(R.raw.users);

        try {
            Users users = serializer.read(Users.class, xmlUsers);
            if (users != null) {
                user = users.getUserList().get(preferences.selectedUser().get());
            }
        } catch (Exception exception) {
            Log.e(TAG, Objects.requireNonNull(exception.getLocalizedMessage()));
        } finally {
            try {
                xmlUsers.close();
            } catch (Exception exception) {
                // not implemented
            }
        }

        return UserData.create().setData(user).getUserData();
    }
}
