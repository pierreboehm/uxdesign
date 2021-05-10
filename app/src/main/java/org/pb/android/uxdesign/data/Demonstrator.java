package org.pb.android.uxdesign.data;

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
import org.pb.android.uxdesign.ui.ViewMode;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

@EBean(scope = EBean.Scope.Singleton)
public class Demonstrator {

    private static final String TAG = Demonstrator.class.getSimpleName();

    @RootContext
    Context context;

    @Pref
    AppPreferences_ preferences;

    private PowerManagerInfo powerManagerInfo;
    private Bitmap accessCodeBitmap;
    private Timer timer;

    @AfterInject
    public void afterInject() {
        powerManagerInfo = new PowerManagerInfo();
    }

    public void start() {
        startTimer();
    }

    public void stop() {
        stopTimer();
    }

    public void setViewMode(ViewMode viewMode) {
        reconfigureTimerTasks(viewMode);
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
        return new PowerManagerInfo();
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

    private void reconfigureTimerTasks(ViewMode viewMode) {

    }

    private void startTimer() {
        timer = new Timer();
        // prepare all needed TimerTasks here
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
