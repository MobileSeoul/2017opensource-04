package com.veryworks.iyeongjun.hkapp.domain;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public class UserItem
{
    private String emailId;

    private String emailToken;

    private String userPsd;

    private String pk;

    public String getEmailId ()
    {
        return emailId;
    }

    public void setEmailId (String emailId)
    {
        this.emailId = emailId;
    }

    public String getEmailToken ()
    {
        return emailToken;
    }

    public void setEmailToken (String emailToken)
    {
        this.emailToken = emailToken;
    }

    public String getUserPsd ()
    {
        return userPsd;
    }

    public void setUserPsd (String userPsd)
    {
        this.userPsd = userPsd;
    }

    public String getPk ()
    {
        return pk;
    }

    public void setPk (String pk)
    {
        this.pk = pk;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [emailId = "+emailId+", emailToken = "+emailToken+", userPsd = "+userPsd+", pk = "+pk+"]";
    }
}

