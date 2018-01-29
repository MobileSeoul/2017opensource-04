package com.veryworks.iyeongjun.hkapp.HTTP;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.veryworks.iyeongjun.hkapp.Interface.BackToMain;
import com.veryworks.iyeongjun.hkapp.Interface.BoardDataInterface;
import com.veryworks.iyeongjun.hkapp.domain.BoardData;
import com.veryworks.iyeongjun.hkapp.domain.BoardItem;
import com.veryworks.iyeongjun.hkapp.domain.Const;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.boardData;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.isInitData;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public class BoardDataLoader {
    Retrofit retrofit;
    Context context;
    Gson gson;
    BoardDataInterface boardDataInterface;
    BackToMain backToMain;
    public BoardDataLoader(Context context) {
        this.context = context;
        if(context instanceof BackToMain) backToMain = (BackToMain) context;
        gson = new Gson();
    }
    public void getBoardData(){
        setRetrofit();
        Call<ResponseBody> result = boardDataInterface.getBoard();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String val1 = "{ \"boardItem\":" +response.body().string()+ "}";
                    Log.d("DataLoad",val1);
                    boardData = gson.fromJson(val1,BoardData.class);
                    Log.d("DataLoad",""+boardData.getBoardItem().length);
                    BoardItem[] items = boardData.getBoardItem();
                    for (int i = 0 ;i < items.length ; i++){
                        items[i].decode();
                        items[i].setBdpk((Integer.parseInt(items[i].getPk())+10000)+"");
                    }
                    isInitData[2] = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void postBoardData(BoardItem item){
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.NETWORK.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        boardDataInterface = retrofit.create(BoardDataInterface.class);


        Call<ResponseBody> call = boardDataInterface.postBoard(item);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context, "등록 되었습니다!", Toast.LENGTH_SHORT).show();
                String var = response.body().toString();
                Log.d("BoardData",var+"//");
                backToMain.backTomain();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void setRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.NETWORK.SERVER_URL)
                .build();
        boardDataInterface
                = retrofit.create(BoardDataInterface.class);
    }

}
