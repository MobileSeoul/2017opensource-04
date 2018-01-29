package com.veryworks.iyeongjun.hkapp.domain;

/**
 * Created by iyeongjun on 2017. 11. 18..
 */

public class Items
{
    private String lon;

    private String title;

    private String hit;

    private String updated;

    private String created;

    private String description;

    private String image;

    private String writer;

    private String type;

    private String lat;

    private String section;

    private String pk;

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getHit ()
    {
        return hit;
    }

    public void setHit (String hit)
    {
        this.hit = hit;
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

    public String getWriter ()
    {
        return writer;
    }

    public void setWriter (String writer)
    {
        this.writer = writer;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    public String getSection ()
    {
        return section;
    }

    public void setSection (String section)
    {
        this.section = section;
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
        return "ClassPojo [lon = "+lon+", title = "+title+", hit = "+hit+", updated = "+updated+", created = "+created+", description = "+description+", image = "+image+", writer = "+writer+", type = "+type+", lat = "+lat+", section = "+section+", pk = "+pk+"]";
    }
}