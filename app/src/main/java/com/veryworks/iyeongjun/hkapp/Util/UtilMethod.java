package com.veryworks.iyeongjun.hkapp.Util;

/**
 * Created by iyeongjun on 2017. 11. 30..
 */

public class UtilMethod {
    public static String setLimitLength(String str, int length){
        String result="";
        String[] arr = str.split("");
        int count = 0;
        for(String node : arr){
            if(count < length) {
                if (node.equals(" ")) count += 1;
                else count += 2;
                result += node;
            }
        }
        return result += "..";
    }
}
