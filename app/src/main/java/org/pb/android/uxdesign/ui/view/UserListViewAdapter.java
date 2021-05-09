package org.pb.android.uxdesign.ui.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.pb.android.uxdesign.data.Demonstrator;
import org.pb.android.uxdesign.data.user.UserData;

import java.util.List;

@EBean
public class UserListViewAdapter extends BaseAdapter {

    @RootContext
    Context context;

    @Bean
    Demonstrator demonstrator;

    private List<UserData> userDataList;

    @AfterInject
    public void afterInject() {
        userDataList = demonstrator.getUserDataList();
    }

    @Override
    public int getCount() {
        return userDataList.size();
    }

    @Override
    public UserData getItem(int position) {
        UserData userData = userDataList.get(position);
        userData.setListPosition(position);
        return userData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserListItemView userListItemView;

        if (convertView == null) {
            userListItemView = UserListItemView_.build(context);
        } else {
            userListItemView = (UserListItemView) convertView;
        }

        userListItemView.bind(getItem(position));
        return userListItemView;
    }
}
