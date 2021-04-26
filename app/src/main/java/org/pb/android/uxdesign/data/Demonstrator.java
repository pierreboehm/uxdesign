package org.pb.android.uxdesign.data;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.data.user.User;
import org.pb.android.uxdesign.data.user.UserData;
import org.pb.android.uxdesign.data.user.Users;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.util.Objects;

@EBean(scope = EBean.Scope.Singleton)
public class Demonstrator {

    private static final String TAG = Demonstrator.class.getSimpleName();

    @RootContext
    Context context;

    public CurrentUser getCurrentUser() {
        UserData userData = loadUserData();
        return new CurrentUser(userData);
    }

    private UserData loadUserData() {
        User user = new User();

        Serializer serializer = new Persister();
        InputStream xmlUsers = context.getResources().openRawResource(R.raw.users);

        try {
            Users users = serializer.read(Users.class, xmlUsers);
            if (users != null) {
                user = users.getUserList().get(0);
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
