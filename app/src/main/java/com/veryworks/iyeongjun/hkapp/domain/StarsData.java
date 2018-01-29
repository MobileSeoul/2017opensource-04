package com.veryworks.iyeongjun.hkapp.domain;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public class StarsData {

    private StarsItem[] starsItem;

    public StarsItem[] getStarsItem ()
    {
        return starsItem;
    }

    public void setStarsItem (StarsItem[] StarsItem)
    {
        this.starsItem = StarsItem;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StarsItem = "+starsItem+"]";
    }
}
