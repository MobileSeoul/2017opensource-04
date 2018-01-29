package com.veryworks.iyeongjun.hkapp.Interface;

import com.veryworks.iyeongjun.hkapp.domain.BoardItem;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public interface BoardDataInterface {

    @GET("/board/")
    Call<ResponseBody> getBoard();

    @POST("/board/")
    Call<ResponseBody> postBoard(@Body BoardItem body);
}
