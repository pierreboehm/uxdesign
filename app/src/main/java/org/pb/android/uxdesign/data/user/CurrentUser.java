package org.pb.android.uxdesign.data.user;

public class CurrentUser {

    private final UserData userData;

    public CurrentUser(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }
}
