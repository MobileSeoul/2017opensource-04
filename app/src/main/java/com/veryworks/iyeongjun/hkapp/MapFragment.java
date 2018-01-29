package com.veryworks.iyeongjun.hkapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.veryworks.iyeongjun.hkapp.EventDriven.RxEventBus;
import com.veryworks.iyeongjun.hkapp.domain.Const;
import com.veryworks.iyeongjun.hkapp.domain.Items;
import com.veryworks.iyeongjun.hkapp.domain.MarkerItem;

import java.util.ArrayList;
import java.util.Arrays;

import static com.veryworks.iyeongjun.hkapp.Util.DataConverter.convertTypeStringToPin;
import static com.veryworks.iyeongjun.hkapp.Util.UserLocation.currentUserLocation;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.hkDatas;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    MapView googleMap;
    LatLng latLng;
    ArrayList<MarkerItem> point = new ArrayList();

    public MapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        googleMap = (MapView)view.findViewById(R.id.googleMap);
        latLng = new LatLng(currentUserLocation.getLatitude(),currentUserLocation.getLongitude());
        setPoint();
        googleMap.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(14));
        for(int i = 0 ; i < point.size()-1; i++) map.addMarker(setMarker(i)).showInfoWindow();
        RxEventBus
                .getInstance()
                .getObservable()
                .subscribe(num -> {if(num == Const.FRAGMENT.MAP) {map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }});
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("type","map");
                intent.putExtra("title",marker.getTitle());
                startActivity(intent);
            }
        });
    }
    private void setPoint(){
        ArrayList<Items> items = new ArrayList<>(Arrays.asList(hkDatas.getItems()));
        for(int i = 0 ; i < items.size() ; i++){
            MarkerItem markerItem = new MarkerItem(
              Double.parseDouble(items.get(i).getLat()),
                    Double.parseDouble(items.get(i).getLon()),
                    items.get(i).getTitle(),
                    Integer.parseInt(items.get(i).getType()),
                    convertTypeStringToPin(Integer.parseInt(items.get(i).getType()))
            );
            point.add(markerItem);
        }
//
    }
    private MarkerOptions setMarker(int i){
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(point.get(i).getLat(), point.get(i).getLon()))
                .title(point.get(i).getTitle())
                .icon(BitmapDescriptorFactory.fromResource(point.get(i).getDrawble()));
        return markerOptions;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        googleMap.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleMap.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        googleMap.onSaveInstanceState(outState);
    }
    @Override
    public void onResume() {
        super.onResume();
        googleMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleMap.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        googleMap.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleMap.onLowMemory();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (googleMap != null) {
            googleMap.onCreate(savedInstanceState);
        }
    }
}