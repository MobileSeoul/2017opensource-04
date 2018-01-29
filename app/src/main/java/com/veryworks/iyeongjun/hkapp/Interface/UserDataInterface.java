package com.veryworks.iyeongjun.hkapp.Interface;

import com.veryworks.iyeongjun.hkapp.domain.UserItem;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public interface UserDataInterface {
    @GET("/user/")
    Call<ResponseBody> getUser();

    @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
    @POST("/user/")
    Call<ResponseBody> postUser(@Body UserItem body);
}
