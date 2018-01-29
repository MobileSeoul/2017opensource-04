package com.veryworks.iyeongjun.hkapp.domain;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public class StarsItem
{
    private String point;

    private String count;

    private String hkpk;

    private String pk;

    public String getPoint ()
    {
        return point;
    }

    public void setPoint (String point)
    {
        this.point = point;
    }

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public String getHkpk ()
    {
        return hkpk;
    }

    public void setHkpk (String hkpk)
    {
        this.hkpk = hkpk;
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
        return "ClassPojo [point = "+point+", count = "+count+", hkpk = "+hkpk+", pk = "+pk+"]";
    }
}