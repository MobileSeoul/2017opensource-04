package com.veryworks.iyeongjun.hkapp.domain;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public class UserData {

    private UserItem[] userItem;

    public UserItem[] getUserItem ()
    {
        return userItem;
    }

    public void setUserItem (UserItem[] UserItem)
    {
        this.userItem = UserItem;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [UserItem = "+userItem+"]";
    }
}
