package com.veryworks.iyeongjun.hkapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.veryworks.iyeongjun.hkapp.HTTP.InitDataLoader;
import com.veryworks.iyeongjun.hkapp.Interface.RedirectToMainWithData;
import com.veryworks.iyeongjun.hkapp.Util.PermissionControl;
import com.veryworks.iyeongjun.hkapp.domain.UserItem;
import com.yqritc.scalablevideoview.ScalableType;
import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.isAllDataRec;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.isGetUserLocation;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.isInitData;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.userData;

public class LoginActivity extends CustomFontAcitivity implements PermissionControl.CallBack, RedirectToMainWithData {

    @BindView(R.id.scalableVideoView)
    ScalableVideoView videoView;
    @BindView(R.id.btnlogin)
    ImageButton btnlogin;
    @BindView(R.id.txtId)
    TextView txtId;
    @BindView(R.id.txtPwd)
    TextView txtPwd;
    @BindView(R.id.imgCreate)
    ImageButton imgCreate;
    @BindView(R.id.imgLater)
    ImageButton imgLater;
    @BindView(R.id.loginback)
    ImageView loginback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setView();
        PermissionControl.checkVersion(this);
    }


    private void setView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, //상태바 제거
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setVideoView();
    }

    private void setVideoView() {
        try {
            videoView.setRawData(R.raw.login);
            videoView.setScalableType(ScalableType.CENTER_CROP);
            videoView.setVolume(0, 0);
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    loginback.setVisibility(View.VISIBLE);
                    return false;
                }
            });
            videoView.setLooping(true);
            videoView.prepare(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    videoView.start();
                    Log.d("VIDEO", "start");
                }
            });
        } catch (IOException e) {
            loginback.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    @OnTouch(R.id.imgLater)
    public boolean laterButtonClicked(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            imgLater.setImageResource(R.drawable.btn_c_later);
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            imgLater.setImageResource(R.drawable.btn_later);
            if (isGetUserLocation && isAllDataRec) redirectMain();
            else if(isGetUserLocation && !isAllDataRec) sendSnack("데이터를 받아오고 있습니다. 잠시 뒤 클릭해주세요");
            else if(!isGetUserLocation && isAllDataRec) sendSnack("GPS를 받아오고 있습니다. 잠시 뒤 클릭해주세요");
            else {
                sendSnack("데이터와 GPS를 받아오고 있습니다.");
            }
        } else {
            imgLater.setImageResource(R.drawable.btn_later);
        }
        return false;
    }

    @OnTouch(R.id.btnlogin)
    public boolean LoginButtonClicked(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            btnlogin.setImageResource(R.drawable.btn_c_login);
            userCheck();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            btnlogin.setImageResource(R.drawable.btn_login);
        } else {
            btnlogin.setImageResource(R.drawable.btn_login);
        }
        return false;
    }

    private void userCheck() {
        int count = 0;
        String name = "";
        if (isGetUserLocation && isGetUserLocation) {
            UserItem[] userItem = userData.getUserItem();
            for (UserItem item : userItem) {
                if (txtId.getText().toString().equals(item.getEmailId())
                        && txtPwd.getText().toString().equals(item.getUserPsd())) {
                    count++;
                    name = item.getEmailId();
                }
            }
            goMainWithLogin(count, name);
        } else{
            sendSnack("데이터를 받아오고 있습니다. 잠시 뒤 클릭해주세요");
        }
    }

    private void goMainWithLogin(int count, String name) {
        if (count == 1) {
            Log.d("UserLogin", "here2?");
            redirectMain();
            Toast.makeText(this
                    , name + "님 환영합니다", Toast.LENGTH_LONG).show();
        } else {
            sendSnack("일치하는 계정이 없습니다.");
        }

    }

    @OnTouch(R.id.imgCreate)
    public boolean createBtnClicekd(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            imgCreate.setImageResource(R.drawable.btn_cleate_acount);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            imgCreate.setImageResource(R.drawable.btn_c_cleate_acount);
            final SignUpDialog dialog = new SignUpDialog(this);
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {

                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {

                }
            });
            dialog.show();
        } else {
            imgCreate.setImageResource(R.drawable.btn_c_cleate_acount);
        }
        return true;
    }

    @Override
    public void init() {
        initData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionControl.onResult(this, requestCode, grantResults);
    }

    @Override
    public void redirectMain() {
        if (isInitData[0] == true
                && isInitData[1] == true
                && isInitData[2] == true
                && isInitData[3] == true) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            sendSnack("데이터를 받아오고 있습니다. 잠시 뒤 클릭해주세요");
        }
    }

    @Override
    public void sendSnack(String str) {
        Snackbar.make(getWindow().getDecorView().getRootView()
                , str, Snackbar.LENGTH_LONG).show();
    }

    private void initData() {
        InitDataLoader initDataLoader = new InitDataLoader(this);
        initDataLoader.initData();
    }

    public boolean sample(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

        } else {

        }
        return true;
    }
}
