package org.pb.android.uxdesign.data.user;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "Users")
public class Users {

    @ElementList(inline = true)
    ArrayList<User> userList;

    public ArrayList<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
        }

        return userList;
    }

}
