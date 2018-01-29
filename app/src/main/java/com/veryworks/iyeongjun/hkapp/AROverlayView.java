package com.veryworks.iyeongjun.hkapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.opengl.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.veryworks.iyeongjun.hkapp.Helper.LocationHelper;
import com.veryworks.iyeongjun.hkapp.Interface.ImageSet;
import com.veryworks.iyeongjun.hkapp.domain.ARPoint;
import com.veryworks.iyeongjun.hkapp.domain.Const;
import com.veryworks.iyeongjun.hkapp.domain.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.veryworks.iyeongjun.hkapp.Util.UserLocation.currentUserLocation;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.hkDatas;


/**
 * Created by ntdat on 1/13/17.
 */

public class AROverlayView extends View implements ARActivity.CheckView, ARActivity.destroyTimer {
    boolean isRedirected = false;
    ImageSet imageSet;
    AppCompatActivity context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private List<ARPoint> arPoints = new ArrayList<>();
    private DisplayMetrics dm = getResources().getDisplayMetrics();
    int width = dm.widthPixels;
    int height = dm.heightPixels;
    int curPos = 0;
    int page;
    boolean[] arr;
    boolean[] temparr;
    Timer timer;

    public AROverlayView(Context context) {
        super(context);
        isRedirected = false;
        this.context = (AppCompatActivity)context;
        imageSet = (ImageSet)context;
        imageSet.setOutImage();
        imageSet.setInImage();
        imageSet.setOutImage();
        currentLocation = currentUserLocation;
        setArPoints(hkDatas.getItems());
        //Demo points
        arr = new boolean[arPoints.size()];
        temparr = new boolean[arPoints.size()];
        for(boolean bool : temparr) bool = false;
        Toast.makeText(context, "GPS와 4G, Wife를 체크하시고, 화면에 안나올 경우" +"\n"+
                "뒤로가기 후 다시 시도해보세요", Toast.LENGTH_LONG).show();
        setTimer();
    }


    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(Location currentLocation){
        this.currentLocation = currentLocation;
        this.invalidate();
    }

    boolean currentImage = Const.AR.OUT_IMAGE;
    int count = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (currentLocation == null) {
            return;
        }
        final int radius = 30;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(60);

        for (int i = 0; i < arPoints.size(); i ++) {
            float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
            float[] pointInECEF = LocationHelper.WSG84toECEF(arPoints.get(i).getLocation());
            float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

            float[] cameraCoordinateVector = new float[4];
            Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);
            // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
            // if z > 0, the point will display on the opposite
            if (cameraCoordinateVector[2] < 0) {
                float x  = (0.5f + cameraCoordinateVector[0]/cameraCoordinateVector[3]) * canvas.getWidth();
                float y = (0.5f - cameraCoordinateVector[1]/cameraCoordinateVector[3]) * canvas.getHeight();
                canvas.drawCircle(x, y, radius, paint);
                canvas.drawText(arPoints.get(i).getName(), x - (30 * arPoints.get(i).getName().length() / 2)
                        , y - 80, paint);
                canvas.drawText(getDistance(arPoints.get(i).getLocation())+"km", x - (30 * arPoints.get(i).getName().length() / 2)
                        , y + 80, paint);
                if(((x < (width/3)*2) && x > (width/3)*1) && ((y < (height/5)*3) && y > (height/5)*2)){
                    arr[i] = true;
                    count++;
                    Log.d("AR","x:" + x + "/y:" + y + "/width:" + width + "/height:" + height );
                }else{
                    arr[i] = false;
                }
            }
        }
        if(count == 0 && currentImage != Const.AR.OUT_IMAGE){
            currentImage = Const.AR.OUT_IMAGE;
            imageSet.setOutImage();
            Log.d("ARImage","Set Out Image");
        } else if(count != 0 && currentImage != Const.AR.IN_IMAGE){
            currentImage = Const.AR.IN_IMAGE;
            imageSet.setInImage();
            Log.d("ARImage","Set In Image");
        }
        count = 0;
    }

    /**
     * View 가 생성되었는지 체크하느 메소드
     */
    @Override
    public void checkView() {

    }

    /**
     * 1초당 사각형 안에 들어온 지점을 체크하는 메소드
     */
    private void setTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("LOCATION","timetask");
                for (int i = 0 ; i < arr.length ; i ++){
                    if(arr[i] && temparr[i]){
                        if (!isRedirected) {
                            Intent intent = new Intent(context,DetailActivity.class);
                            intent.putExtra("type","map");
                            intent.putExtra("title",arPoints.get(i).getName());
                            context.startActivity(intent);
                            context.finish();
                            isRedirected = true;
                        }
                    }
                        temparr[i] = arr[i];
                    }
                }
        },0,1000);
    }
    private void setArPoints(Items[] datas){
        Random random = new Random();
        Location mLocation;
        ARPoint arPoint;
        if(datas.length > 20) page = 20;
        else page = datas.length;
        for(int i= 0 ; i <page ; i++){
            mLocation = makeLocation(datas[i]);
//            if(getDistance(mLocation) < 3.0){
                arPoint = new ARPoint(
                        datas[i].getTitle(),
                        Double.parseDouble(datas[i].getLat()),
                        Double.parseDouble(datas[i].getLon()),
                        ((double)(random.nextInt()%150)));
                        Log.d("ARPOINT"
                        ,Double.parseDouble(datas[i].getLat())+"/"+
                                Double.parseDouble(datas[i].getLon())+"");
                        arPoints.add(arPoint);
            }
//        }
    }
    private Location makeLocation(Items item){
        Location location = new Location("Location");
        location.setLatitude(Double.parseDouble(item.getLat()));
        location.setLongitude(Double.parseDouble(item.getLon()));
        location.setAltitude(0.0);
        return location;
    }
    private double getDistance(Location location){
        return ((int)(currentLocation.distanceTo(location)))/(1000.0);
    }
    @Override
    public void destoryTimer() {
        timer.cancel();
    }
    private int getMyColor(int i){
        return getResources().getColor(i);
    }
}

