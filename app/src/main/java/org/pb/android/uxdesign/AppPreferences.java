package org.pb.android.uxdesign;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface AppPreferences {
    @DefaultInt(0)
    int selectedUser();
}
