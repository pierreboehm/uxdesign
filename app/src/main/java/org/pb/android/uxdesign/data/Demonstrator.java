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

import org.pb.android.uxdesign.util.Util;
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

    private static final float ENV_TEMP_MIN = 20f;
    private static final float ENV_TEMP_MAX = 38f;

    private static final float AIR_PRESS_MIN = 980f;
    private static final float AIR_PRESS_MAX = 1045f;

    @RootContext
    Context context;

    @Pref
    AppPreferences_ preferences;

    private PowerManagerInfo powerManagerInfo;
    private Bitmap accessCodeBitmap, n2StatusBitmap, o2StatusBitmap, arStatusBitmap, co2StatusBitmap;

    private float n2Level, o2level, arLevel, co2Level;

    private long timestampStart;
    private int errors = 0;
    private int failures = 0;
    private int timers = 0;

    private float airTemperature;
    private float airPressure;
    private float airHumidity = -1f;

    @AfterInject
    public void afterInject() {
        powerManagerInfo = new PowerManagerInfo();
        timestampStart = System.currentTimeMillis();

        airTemperature = (float) Util.roundScale(Util.getRandomBetween(ENV_TEMP_MIN, ENV_TEMP_MIN + (ENV_TEMP_MIN * .5f)));
        airPressure = (float) Util.roundScale(Util.getRandomBetween(AIR_PRESS_MIN, AIR_PRESS_MIN + (AIR_PRESS_MIN * .5f)));
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

    public int getTimersDefined() {
        return 4;
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

    public void setN2Level(float value) {
        n2Level = value;
    }

    public float getN2Level() {
        return n2Level;
    }

    public void setO2level(float value) {
        o2level = value;
    }

    public float getO2Level() {
        return o2level;
    }

    public void setArLevel(float value) {
        arLevel = value;
    }

    public float getArLevel() {
        return arLevel;
    }

    public void setCo2Level(float value) {
        co2Level = value;
    }

    public float getCo2Level() {
        return co2Level;
    }

    public void setN2StatusBitmap(Bitmap bitmap) {
        n2StatusBitmap = bitmap;
    }

    public Bitmap getN2StatusBitmap() {
        return n2StatusBitmap;
    }

    public void setO2StatusBitmap(Bitmap bitmap) {
        o2StatusBitmap = bitmap;
    }

    public Bitmap getO2StatusBitmap() {
        return o2StatusBitmap;
    }

    public void setArStatusBitmap(Bitmap bitmap) {
        arStatusBitmap = bitmap;
    }

    public Bitmap getArStatusBitmap() {
        return arStatusBitmap;
    }

    public void setCo2StatusBitmap(Bitmap bitmap) {
        co2StatusBitmap = bitmap;
    }

    public Bitmap getCo2StatusBitmap() {
        return co2StatusBitmap;
    }

    public float getAirTemperature() {
        airTemperature += (float) Util.getRandomBetween(-2, 2) / 100f;

        airTemperature = Math.min(airTemperature, ENV_TEMP_MAX);
        airTemperature = Math.max(airTemperature, ENV_TEMP_MIN);

        return (float) Util.roundScale(airTemperature);
    }

    public float getAirPressure() {
        airPressure += (float) Util.getRandomBetween(-2, -2) / 100f;

        airPressure = Math.min(airPressure, AIR_PRESS_MAX);
        airPressure = Math.max(airPressure, AIR_PRESS_MIN);

        return (float) Util.roundScale(airPressure);
    }

    public float getAirSaturation() {
        float exponent = ((17.62f * airTemperature) / (243.12f + airTemperature));
        float magnus = 6.112f * (float) Math.exp(exponent);

        return (magnus / (461.51f * (airTemperature + 273.15f))) * 100000f;
    }

    public float getAirHumidityAbsolute() {
        return airHumidity;
    }

    public float getAirHumidityRelative() {
        return airHumidity;
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

    public boolean isPlugged() {
        return powerManagerInfo.isPluggedIn();
    }

    private List<User> getUserList() {
        List<User> resultList = new ArrayList<>();

        Serializer serializer = new Persister();
        InputStream xmlUsers = context.getResources().openRawResource(R.raw.users2);

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
        InputStream xmlUsers = context.getResources().openRawResource(R.raw.users2);

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
