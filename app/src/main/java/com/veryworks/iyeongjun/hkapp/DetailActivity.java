package com.veryworks.iyeongjun.hkapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.veryworks.iyeongjun.hkapp.HTTP.StarDataLoader;
import com.veryworks.iyeongjun.hkapp.domain.BoardData;
import com.veryworks.iyeongjun.hkapp.domain.BoardItem;
import com.veryworks.iyeongjun.hkapp.domain.Items;
import com.veryworks.iyeongjun.hkapp.domain.MarkerItem;
import com.veryworks.iyeongjun.hkapp.domain.StarsItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.veryworks.iyeongjun.hkapp.Util.DataConverter.convertSectionIntToString;
import static com.veryworks.iyeongjun.hkapp.Util.DataConverter.convertTypeIntToString;
import static com.veryworks.iyeongjun.hkapp.Util.DataConverter.convertTypeStringToPin;
import static com.veryworks.iyeongjun.hkapp.Util.UserLocation.currentUserLocation;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.boardData;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.hkDatas;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.starsData;

public class DetailActivity extends CustomFontAcitivity implements OnMapReadyCallback {

    @BindView(R.id.deBtnBack)
    ImageButton deBtnBack;
    @BindView(R.id.detailImage)
    ImageView detailImage;
    @BindView(R.id.deTxtTitle)
    TextView detxtTitle;
    @BindView(R.id.deTxtStars)
    TextView deTxtStars;
    @BindView(R.id.deTxtSub)
    TextView deTxtSub;
    @BindView(R.id.detxtContents)
    TextView detxtContents;
    @BindView(R.id.ratingBar)
    RatingBar ratingVar;
    @BindView(R.id.deTxtType)
    TextView deTxtType;
    @BindView(R.id.deTxtDistance)
    TextView deTxtDistance;
    @BindView(R.id.line_map)
    ImageView lineMap;
    @BindView(R.id.imageView9)
    ImageView imageView9;

    Items[] items;
    Items data;
    LatLng latlng;
    MarkerItem markerItem;
    String pk;
    String starPk;
    float starPoint;
    int starCount;
    StarsItem current;

    BoardItem boardItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, //상태바 제거
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setItems();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.deMap);
        setView();
        mapFragment.getMapAsync(this);
    }

    private void setItems() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        items = hkDatas.getItems();
        if (type.equals("list")) {
            pk = intent.getStringExtra("dataPk");
            for (Items item : items) {
                if (Integer.parseInt(item.getPk()) == Integer.parseInt(pk)) {
                    data = item;
                    latlng = new LatLng(Double.parseDouble(data.getLat())
                            , Double.parseDouble(data.getLon()));
                }
            }
        } else if (type.equals("board")) {


        } else if (type.equals("map")){
            String title = intent.getStringExtra("title");
            for(Items item : items){
                if(title.equals(item.getTitle())){
                    pk = item.getPk();
                    data = item;
                    latlng = new LatLng(Double.parseDouble(data.getLat())
                            , Double.parseDouble(data.getLon()));
                }

            }
        }
        for(StarsItem item : starsData.getStarsItem()){
            if(item.getHkpk().equals(pk)) {
                current = item;
                starPoint  = Float.parseFloat(item.getPoint());
                starCount = Integer.parseInt(item.getCount());
                starPk = item.getPk();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        map.animateCamera(CameraUpdateFactory.zoomTo(14));
        map.addMarker(setMarker()).showInfoWindow();
    }

    private void setPoint() {
        markerItem = new MarkerItem(
                Double.parseDouble(data.getLat()),
                Double.parseDouble(data.getLon()),
                data.getTitle(),
                Integer.parseInt(data.getType()),
                convertTypeStringToPin(Integer.parseInt(data.getType()))
        );
    }

    private MarkerOptions setMarker() {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(markerItem.getLat(), markerItem.getLon()))
                .title(markerItem.getTitle())
                .icon(BitmapDescriptorFactory.fromResource(markerItem.getDrawble()));
        return markerOptions;
    }

    private void setView() {
        Glide.with(this)
                .load(data.getImage())
                .into(detailImage);
        setRating();
        setPoint();
        setText();
    }

    private void setText() {
        detxtTitle.setText(data.getTitle());
        deTxtSub.setText("   {fa-map-marker} 지역 :  " + convertSectionIntToString(Integer.parseInt(data.getSection())));
        deTxtType.setText("   {fa-th-large} 분류 : " + convertTypeIntToString(Integer.parseInt(data.getType())));
        deTxtDistance.setText("   {fa-compass} 거리 : " + getDistance(makeLocation(data)));
        if(starPoint != 0.0){
            deTxtStars.setText("   {fa-star} 별점 : " + starPoint);
        }else{
            deTxtStars.setText("   {fa-star} 별점 : " + 0.0);
        }
        detxtContents.setText(data.getDescription());
    }

    private void setRating() {
        ratingVar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("평가하기");
                builder.setMessage(v+"점을 주셨습니다! 평가하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        putStar(v);
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
    }
    private void putStar(float v){
        boolean result = false;
        StarDataLoader dataLoader = new StarDataLoader(this);
        StarsItem starsItem = new StarsItem();
        for(StarsItem item : starsData.getStarsItem()){
            if(item.getHkpk().equals(pk)) result = true;
        }

        if(result){
            starsItem.setHkpk(pk+"");
            starsItem.setPoint(setPoint(current,(double)v)+"");
            starsItem.setCount((starCount+1)+"");
            starsItem.setPk(starPk);
            dataLoader.putStarsData(starsItem);
        }else{
            starsItem.setHkpk(pk+"");
            starsItem.setPoint(v+"");
            starsItem.setCount("1");
            starsItem.setPk("10000");
            dataLoader.postStarsData(starsItem);
        }
    }

    private String getDistance(Location location) {
        double distance = ((int) (currentUserLocation.distanceTo(location))) / (1000.0);
        return distance + "km";
    }

    private Items changeItem(BoardItem data) {
        Items item = new Items();
        item.setCreated(data.getCreated());
        item.setDescription(data.getDescription());
        item.setPk(data.getBdpk());
        item.setUpdated(data.getUpdated());
        item.setImage(data.getImage());
        item.setLat("37.526793");
        item.setLon("126.981671");
        item.setTitle(data.getTitle());
        item.setSection("");
        return item;
    }

    private Location makeLocation(Items item) {
        Location location = new Location("Location");
        location.setLatitude(Double.parseDouble(item.getLat()));
        location.setLongitude(Double.parseDouble(item.getLon()));
        location.setAltitude(0.0);
        return location;
    }
    private double setPoint(StarsItem item,double v){
        double itemP = Double.parseDouble(item.getPoint());
        int tempcount = Integer.parseInt(item.getCount());
        double count = (double)tempcount;
        double a1 = v/count;
        double a2 = itemP+a1;
        double a3 = (count/(count+1));
        double result = a3*a2 ;
        Log.d("Mathaaa",a1 + "/"+ a2 +"/" + a3 + "/"+ result);
        return ((count/(count+1))*(itemP+(v/count)));
    }

    @OnClick(R.id.deBtnBack)
    public void setDeBtnBack(){
        super.onBackPressed();
    }
}
