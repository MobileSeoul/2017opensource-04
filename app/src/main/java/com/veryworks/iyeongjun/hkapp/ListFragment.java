package com.veryworks.iyeongjun.hkapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.veryworks.iyeongjun.hkapp.EventDriven.RxEventBus;
import com.veryworks.iyeongjun.hkapp.Interface.SetBackgroundListFragment;
import com.veryworks.iyeongjun.hkapp.Util.CustomBitmapPool;
import com.veryworks.iyeongjun.hkapp.adapter.ListAdapter;
import com.veryworks.iyeongjun.hkapp.domain.Const;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.hkDatas;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements SetBackgroundListFragment {

    @BindView(R.id.imgListBack) ImageView imgListBack;
    @BindView(R.id.listRecycler) RecyclerView listRecycler;
    @BindView(R.id.frameLayout) FrameLayout frameLayout;
    ListAdapter listAdapter;
    Unbinder unbinder;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        RxEventBus
                .getInstance()
                .getObservable()
                .subscribe(num -> {if(num == Const.FRAGMENT.LIST) {fabClicked(num);}});
        setRecycler();
        return view;
    }
    private void fabClicked(int num) {
        listAdapter.notifyDataSetChanged();
    }
    private void setRecycler() {
        if(hkDatas != null && hkDatas.getItems().length >0){
            listAdapter = new ListAdapter(getActivity(),this,hkDatas);
        }
        listRecycler.setAdapter(listAdapter);
        listRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setBackground(String img) {
        Glide
                .with(getActivity())
                .load(img)
                .crossFade(1000)
                .bitmapTransform(new ColorFilterTransformation(new CustomBitmapPool(), Color.argb(150, 0, 0, 0)))
                .into(imgListBack);
    }
}
