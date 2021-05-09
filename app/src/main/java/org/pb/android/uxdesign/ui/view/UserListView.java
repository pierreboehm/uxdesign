package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ItemClick;

import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.UserData;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_user_list)
public class UserListView extends LinearLayout {

    private static final String TAG = UserListView.class.getSimpleName();

    @ViewById(R.id.lvItemContainer)
    ListView itemContainer;

    @Bean
    UserListViewAdapter userListViewAdapter;

    public UserListView(@NonNull Context context) {
        super(context);
    }

    @AfterViews
    public void initView() {
        itemContainer.setDivider(null);
        itemContainer.setAdapter(userListViewAdapter);
    }

    @ItemClick(R.id.lvItemContainer)
    public void onItemClick(UserData userData) {
        Log.d(TAG, "name = " + userData.getName());
    }
}
