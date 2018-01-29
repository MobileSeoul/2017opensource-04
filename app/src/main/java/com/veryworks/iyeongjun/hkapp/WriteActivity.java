package com.veryworks.iyeongjun.hkapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.veryworks.iyeongjun.hkapp.HTTP.BoardDataLoader;
import com.veryworks.iyeongjun.hkapp.Interface.BackToMain;
import com.veryworks.iyeongjun.hkapp.domain.BoardItem;
import com.veryworks.iyeongjun.hkapp.domain.Const;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class WriteActivity extends AppCompatActivity implements BackToMain {
    private static final int REQ_CODE_SELECT_IMAGE = 100;
    private static final int SELECT_PICTURE = 1;

    @BindView(R.id.ediTtitle) EditText ediTtitle;
    @BindView(R.id.imageView10) ImageView imageView10;
    @BindView(R.id.editContents) EditText editContents;
    @BindView(R.id.imgPreview) ImageView imgPreview;
    @BindView(R.id.wtBtnUpload) ImageButton wtBtnUpload;
    @BindView(R.id.wtBtnSelect) ImageButton selectBtn;

    boolean isFirst = true;
    private String selectedImagePath;

    private String TAG = "MainActivity";

    CognitoCachingCredentialsProvider credentialsProvider;
    AmazonS3 s3;
    TransferUtility transferUtility;
    File f;

    private String userChoosenTask;
    Uri imageUri;
    String imagePath;
    private Uri mImageUri;
    private int PICTURE_CHOICE = 1;
    private int REQUEST_CAMERA = 2;
    private int SELECT_FILE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, //상태바 제거
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);
        credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "ap-northeast-2:03c19e0f-3f37-45ba-a0ce-511d37b9947b", // 자격 증명 풀 ID
                Regions.AP_NORTHEAST_2 // 리전
        );
        s3 = new AmazonS3Client(credentialsProvider);
        s3.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
        s3.setEndpoint("s3.ap-northeast-2.amazonaws.com");

        transferUtility = new TransferUtility(s3, getApplicationContext());

    }

    @OnTouch(R.id.wtBtnSelect)
    public boolean selectClicekd(MotionEvent event) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
        return false;
    }

    @OnTouch(R.id.wtBtnUpload)
    public boolean uploadClicekd(MotionEvent event) {
        if (selectedImagePath != null) {

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    f = new File(selectedImagePath);
                    TransferObserver observer = transferUtility.upload(
                            "ishkstorage",
                            f.getName(),
                            f);
                    observer.setTransferListener(new TransferListener() {
                        @Override
                        public void onStateChanged(int id, TransferState state) {
                            Log.d("AWS", "onstate" + id + " / " + state);

                            if (state.toString().equals("COMPLETED")) {
                                if(editContents.getText().toString().length()==0 ||
                                        ediTtitle.getText().toString().length()==0){
                                    Toast.makeText(WriteActivity.this, "제목 또는 본문이 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
                                }else{
                                    if(isFirst){
                                        isFirst = false;
                                        BoardItem boardItem = new BoardItem();
                                        boardItem.setBdpk("200");
                                        boardItem.setTitle(ediTtitle.getText().toString());
                                        boardItem.setDescription("|" + editContents.getText().toString()+"$"+Const.NETWORK.BUCKET_URL+f.getName());
//                                    Log.d("AWS",boardItem.getImage());
                                        BoardDataLoader dataLoader = new BoardDataLoader(WriteActivity.this);
                                        dataLoader.postBoardData(boardItem);
                                    }
                                }
                            }
                        }
                        @Override
                        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                            Log.d("AWS", "onProgressChanged" + id + " / " + bytesCurrent + "/" + bytesTotal);
                        }

                        @Override
                        public void onError(int id, Exception ex) {
                            Log.d("AWS", "OnError" + id + " / " + ex.toString());
                            ex.printStackTrace();
                        }
                    });
                    return null;
                }
            }.execute();
        } else {
            Toast.makeText(this, "사진을 먼저 선택해야합니다", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Log.d("IMAGESET", selectedImagePath);
                try {
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    imgPreview.setVisibility(View.VISIBLE);
                    imgPreview.setImageBitmap(image_bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getPath(Uri uri) {

        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    @Override
    public void backTomain() {
        Intent intent = new Intent(WriteActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}