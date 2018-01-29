package com.veryworks.iyeongjun.hkapp.HTTP;


/*데이터 로더 클래스*/

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.veryworks.iyeongjun.hkapp.domain.Const;
import com.veryworks.iyeongjun.hkapp.domain.HKData;
import com.veryworks.iyeongjun.hkapp.domain.Items;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.hkDatas;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.isInitData;

public class DataReceiver {
    Retrofit retrofit;
    Context context;
    Gson gson;
    public DataReceiver(Context context) {
        gson = new Gson();
        this.context = context;
    }

    /* DJango 서버에 저장된 한강 데이터를 가져옮 */
    public void getData(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.NETWORK.SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HKdataReceiveInterface hKdataReceiveInterface
                =retrofit.create(HKdataReceiveInterface.class);
        Call<ResponseBody> result = hKdataReceiveInterface.getHKData();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String val1 = "{ \"items\":" +response.body().string()+ "}";
                    Log.d("data",val1);
                    hkDatas = gson.fromJson(val1,HKData.class);
                    Items[] items = hkDatas.getItems();
                    for (int i = 0 ; i < items.length ; i ++){
                        Log.d("data",
                                i +"/" +
                                items[i].getTitle()+"/"+
                                items[i].getImage()+" /"+
                                items[i].getSection());
                    }
                    isInitData[0] = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(t.getMessage());
                t.printStackTrace();
                Toast.makeText(context, "데이터 읽기 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    private void ifSeccess(Response<Items> response){
//        Items[] items = response.body();
//        if(datas != null) datas.clear();
//        for(int i = 0 ; i < items.length ; i ++){
//            datas.add(items[i]);
//            Log.d("Data",i+datas.get(i).getTitle()+"/"+
//                    i+datas.get(i).getTel()+"/"+
//                    i+datas.get(i).getSigungucode()+"/"+
//                    i+datas.get(i).getDist()+"/"+
//                    i+datas.get(i).getFirstimage()+"/"+
//                    i+datas.get(i).getFirstimage2()+"/"+
//                    i+datas.get(i).getAddr1()+"/"+
//                    i+datas.get(i).getAddr2()+"/"+
//                    i+datas.get(i).getTel()+"/"
//                    +datas.get(i).getContenttypeid());
//            Log.d("Image",datas.get(i).getFirstimage2()+"/"+datas.get(i).getContenttypeid()+"/");
//        }
//        if (context instanceof CompleteData){
//            ((CompleteData)context).dataReceieveComplete();
//            Log.d("Callback","callback");
//        }else{
//            Log.d("Callback","not callback");
//        }
//    }
    interface HKdataReceiveInterface{
        @GET("/data")
        Call<ResponseBody> getHKData();
    }
}
