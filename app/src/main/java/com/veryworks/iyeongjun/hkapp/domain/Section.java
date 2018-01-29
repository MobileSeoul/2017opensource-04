package com.veryworks.iyeongjun.hkapp.domain;

/**
 * Created by iyeongjun on 2017. 11. 23..
 */

public class Section {
    private int pk;
    private String name;
    private Double lat;
    private Double lng;

    public Section() {
    }

    public Section(int pk, String name, Double lat, Double lng) {
        this.pk = pk;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
