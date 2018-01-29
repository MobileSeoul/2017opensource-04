package com.veryworks.iyeongjun.hkapp.HTTP;

import android.content.Context;
import android.net.Uri;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

import static com.veryworks.iyeongjun.hkapp.domain.Const.NETWORK.AWS_ACCESS_KEY_ID;
import static com.veryworks.iyeongjun.hkapp.domain.Const.NETWORK.AWS_SECRET_ACCESS_KEY;
import static com.veryworks.iyeongjun.hkapp.domain.Const.NETWORK.BUCKET_NAME;


/**
 * Created by iyeongjun on 2017. 11. 28..
 */

public class AWSService {
    private AmazonS3 amazonS3;
    Context context;
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


    public AWSService(Context context) {
        this.context = context;
        AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY_ID,AWS_SECRET_ACCESS_KEY);
        amazonS3 = new AmazonS3Client(awsCredentials);
        TransferUtility transferUtility = new TransferUtility(s3, context);
    }

    public void uploadFile(File file){
        if (amazonS3 != null) {
            try {
                PutObjectRequest putObjectRequest =
                        new PutObjectRequest(BUCKET_NAME + "/BoardImg", file.getName(), file);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // file permission
                amazonS3.putObject(putObjectRequest); // upload file
            } catch (AmazonServiceException ase) {
                ase.printStackTrace();
            } finally {
                amazonS3 = null;
            }
        }
    }


}
