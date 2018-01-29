package com.veryworks.iyeongjun.hkapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.veryworks.iyeongjun.hkapp.Interface.SetBackgroundListFragment;
import com.veryworks.iyeongjun.hkapp.adapter.ListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.hkDatas;

public class ListActivity extends CustomFontAcitivity implements SetBackgroundListFragment {

    @BindView(R.id.listActivityRecycler) RecyclerView listActivityRecycler;
    ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, //상태바 제거
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String setting = intent.getStringExtra("setting");
        String content = intent.getStringExtra("content");
        Log.d("LISTACTIVITY",setting+"/"+content);
        setRecycler(setting,content);
    }
    private void setRecycler(String setting, String content) {
        listAdapter = new ListAdapter(this,this,hkDatas,setting,content);
        listActivityRecycler.setAdapter(listAdapter);
        listActivityRecycler.setLayoutManager(new LinearLayoutManager(this));
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void setBackground(String img) {
        Glide
                .with(this)
                .load(img)
                .asBitmap();
    }
}
