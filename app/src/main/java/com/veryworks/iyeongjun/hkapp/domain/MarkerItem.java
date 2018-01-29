package com.veryworks.iyeongjun.hkapp.domain;

/**
 * Created by iyeongjun on 2017. 11. 3..
 */

public class MarkerItem {
    private double lat;
    private double lon;
    private String title;
    private int type;
    private int drawble;

    public MarkerItem(double lat, double lon, String title, int type, int drawble) {
        this.lat = lat;
        this.lon = lon;
        this.title = title;
        this.type = type;
        this.drawble = drawble;
    }

    public int getDrawble() {
        return drawble;
    }

    public void setDrawble(int drawble) {
        this.drawble = drawble;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
