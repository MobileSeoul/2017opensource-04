package com.veryworks.iyeongjun.hkapp.Interface;

import com.veryworks.iyeongjun.hkapp.domain.StarsItem;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public interface StarDataInterface {
    @PUT("/stars/{pk}/")
    Call<ResponseBody> putStars(@Path("pk") int pk,
                                @Body StarsItem body);
    @POST("/stars/")
    Call<ResponseBody> postStars(@Body StarsItem body);

    @GET("/stars/")
    Call<ResponseBody> getStars();
}
