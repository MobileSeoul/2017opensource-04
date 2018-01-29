package com.veryworks.iyeongjun.hkapp.domain;

import android.util.Log;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */
public class BoardItem
{
    private String title;

    private String updated;

    private String created;

    private String description;

    private String image;

    private String bdpk;

    private String pk;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getUpdated ()
    {
        return updated;
    }

    public void setUpdated (String updated)
    {
        this.updated = updated;
    }

    public String getCreated ()
    {
        return created;
    }

    public void setCreated (String created)
    {
        this.created = created;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getBdpk ()
    {
        return bdpk;
    }

    public void setBdpk (String bdpk)
    {
        this.bdpk = bdpk;
    }

    public String getPk ()
    {
        return pk;
    }

    public void setPk (String pk)
    {
        this.pk = pk;
    }

    public void decode(){
        if(getDescription().startsWith("|")){
            String[] arr = getDescription().split("\\$");
            for(int i = 0; i <arr.length ; i++ ){
                Log.d("DECODE",i+"/"+arr[i]);
            }
            setDescription((arr[0].replaceFirst("\\|","")));
            setImage(arr[1]);
            Log.d("DECODE",getDescription() + " / " + getImage() + arr);
        }
    }
    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", updated = "+updated+", created = "+created+", description = "+description+", image = "+image+", bdpk = "+bdpk+", pk = "+pk+"]";
    }
}