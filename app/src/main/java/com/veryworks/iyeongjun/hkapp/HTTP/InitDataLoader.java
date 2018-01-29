package com.veryworks.iyeongjun.hkapp.HTTP;

import android.content.Context;

import com.veryworks.iyeongjun.hkapp.Interface.RedirectToMainWithData;
import com.veryworks.iyeongjun.hkapp.Util.UserLocation;

import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.isAllDataRec;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public class InitDataLoader {
    Context context;
    RedirectToMainWithData redirectToMainWithData;
    boolean flag = true;
    public InitDataLoader(Context context, boolean isMain){
        this.context = context;
    }
    public InitDataLoader(Context context) {
        this.context = context;
        redirectToMainWithData = (RedirectToMainWithData) context;
    }
    public void initData(){
        UserLocation userLocation = new UserLocation(context);
        DataReceiver dataReceiver = new DataReceiver(context);
        BoardDataLoader boardDataLoader = new BoardDataLoader(context);
        UserDataLoader userDataLoader = new UserDataLoader(context);
        StarDataLoader starDataLoader = new StarDataLoader(context);
        userDataLoader.getuserData();
        starDataLoader.getStarsData();
        userLocation.getLocation();
        boardDataLoader.getBoardData();
        dataReceiver.getData();
        isAllDataRec = true;

//        while (flag){
//            if(isInitData[0] == true && isInitData[1] == true && isInitData[2] == true && isInitData[3] == true){
//                Log.d("DataLoad",isInitData[0] +"/"+isInitData[0] +"/"+isInitData[0] +"/"+isInitData[0] +"/");
//                isAllDataRec = true;
//                flag = false;
//            }else{
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
