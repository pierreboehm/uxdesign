package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.UserData;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_user_list_item)
public class UserListItemView extends RelativeLayout {

    @ViewById(R.id.tvUserName)
    TextView tvUserName;

    public UserListItemView(Context context) {
        super(context);
    }

    public void bind(UserData userData) {
        tvUserName.setText(userData.getName());
    }
}
