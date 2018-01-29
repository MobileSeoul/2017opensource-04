package com.veryworks.iyeongjun.hkapp.HTTP;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.veryworks.iyeongjun.hkapp.Interface.SignUpToastInterface;
import com.veryworks.iyeongjun.hkapp.Interface.UserDataInterface;
import com.veryworks.iyeongjun.hkapp.domain.Const;
import com.veryworks.iyeongjun.hkapp.domain.UserData;
import com.veryworks.iyeongjun.hkapp.domain.UserItem;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.isInitData;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.userData;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public class UserDataLoader {
    Retrofit retrofit;
    Context context;
    Gson gson;
    UserDataInterface userDataInterface;
    SignUpToastInterface signUpToastInterface;
    public UserDataLoader(Context context) {
        this.context = context;
        gson = new Gson();
    }
    public UserDataLoader(Context context,SignUpToastInterface signUpToastInterface) {
        this.context = context;
        this.signUpToastInterface = signUpToastInterface;
        gson = new Gson();
    }
    public void getuserData(){
        setRetrofit();
        Call<ResponseBody> result = userDataInterface.getUser();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String val1 = "{ \"userItem\":" +response.body().string()+ "}";
                    Log.d("DataLoad",val1);
                    userData = gson.fromJson(val1,UserData.class);
                    Log.d("DataLoad",""+userData.getUserItem().length);
                    UserItem[] items = userData.getUserItem();
                    for (int i = 0 ;i < items.length ; i++){
                        Log.d("DataLoad",items[i].getEmailId());
                    }
                    isInitData[3] = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void postUserData(UserItem userItem){
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.NETWORK.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userDataInterface
                = retrofit.create(UserDataInterface.class);
        Call<ResponseBody> call = userDataInterface.postUser(userItem);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String var = response.body().toString();
                Log.d("Postdata",var + "//");
                if(signUpToastInterface != null) signUpToastInterface.sucess();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Postdata","Failed" + "//");
                if(signUpToastInterface != null) signUpToastInterface.failed();
            }
        });
    }
    private void setRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.NETWORK.SERVER_URL)
                .build();
        userDataInterface
                = retrofit.create(UserDataInterface.class);
    }
}
