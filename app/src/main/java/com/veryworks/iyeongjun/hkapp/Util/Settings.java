package com.veryworks.iyeongjun.hkapp.Util;

/**
 * Created by iyeongjun on 2017. 10. 31..
 */

public class Settings {
    private static UserSetting instance;

    private Settings(){

    }
    public static UserSetting getInstance(){
        if(instance ==null){
            instance = new UserSetting();
        }
        return instance;
    }

//    private static NewsData instance;
//    private Data data;
//    private NewsData(){
//
//    }
//
//    public static NewsData getInstance(){
//        if (instance ==null){
//            instance = new NewsData();
//        }
//        return instance;
//    }
//
//    public Data getData() {
//        return data;
//    }
//
//    public void setData(Data data) {
//        this.data = data;
//    }

}
