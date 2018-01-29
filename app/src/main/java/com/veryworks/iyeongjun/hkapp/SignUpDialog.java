package com.veryworks.iyeongjun.hkapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.veryworks.iyeongjun.hkapp.HTTP.UserDataLoader;
import com.veryworks.iyeongjun.hkapp.Interface.RedirectToMainWithData;
import com.veryworks.iyeongjun.hkapp.Interface.SignUpToastInterface;
import com.veryworks.iyeongjun.hkapp.domain.UserItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by iyeongjun on 2017. 11. 28..
 */
//alt + shift + f -> 즐겨찾기 가능
public class SignUpDialog extends Dialog implements SignUpToastInterface {

    @BindView(R.id.dialogPwd) EditText dialogPwd;
    @BindView(R.id.dialogEmail) EditText dialogEmail;
    @BindView(R.id.dialogAgain) EditText dialogAgain;
    @BindView(R.id.dialogBtnSignUp) ImageButton dialogBtnSignUp;

    Context context;
    RedirectToMainWithData redirectMain;

    public SignUpDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        redirectMain = (RedirectToMainWithData) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_signup);
        ButterKnife.bind(this);
    }

    @OnTouch(R.id.dialogBtnSignUp)
    public boolean signUpClicked(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            dialogBtnSignUp.setImageResource(R.drawable.dialog_signup_c_btn);
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            dialogBtnSignUp.setImageResource(R.drawable.dialog_signup_btn);
            postSignUp(dialogEmail.getText().toString(),
                    dialogPwd.getText().toString(),
                    dialogAgain.getText().toString());
        } else {
            dialogBtnSignUp.setImageResource(R.drawable.dialog_signup_btn);
        }
        return false;
    }

    private void postSignUp(String email, String pwd, String pwdAgain) {
        Log.d("Dialog", email + "/" + pwd + "/" + pwdAgain);
        String[] strArr = email.split("");
        int count = 0;
        for (String str : strArr) {
            if (str.equals("@")) count++;
        }
        if (!pwd.equals(pwdAgain))
            Toast.makeText(context, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
        else if (count == 0 || count != 1) {
            Toast.makeText(context, "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
            Log.d("Dialog", "not email");
        } else if (email.length() < 10 || pwd.length() < 8) {
            Toast.makeText(context, "이메일과 비밀번호는 8자 이상이어야합니다", Toast.LENGTH_SHORT).show();

        } else {
            UserDataLoader loader = new UserDataLoader(context, this);
            UserItem useritem = new UserItem();
            useritem.setEmailId(email);
            useritem.setUserPsd(pwd);
            useritem.setEmailToken("email");
            loader.postUserData(useritem);
        }
    }

    @Override
    public void sucess() {
        Toast.makeText(context, "가입에 성공하였습니다!", Toast.LENGTH_SHORT).show();
        dismiss();
        redirectMain.redirectMain();
    }

    @Override
    public void failed() {
        Toast.makeText(context, "가입에 실패하였습니다", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }
}
