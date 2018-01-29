package com.veryworks.iyeongjun.hkapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.veryworks.iyeongjun.hkapp.HTTP.StarDataLoader;
import com.veryworks.iyeongjun.hkapp.domain.BoardItem;
import com.veryworks.iyeongjun.hkapp.domain.StarsItem;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.boardData;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.starsData;

/**
 * Created by iyeongjun on 2017. 12. 1..
 */

public class BoardDialog extends Dialog {

    @BindView(R.id.dialogTitle) TextView dialogTitle;
    @BindView(R.id.dialogDescription) TextView dialogDescription;
    @BindView(R.id.dialogRating) RatingBar dialogRating;

    Context context;
    int postion;
    boolean isFirstTime = true;
    BoardItem[] boardItems;
    public BoardDialog(@NonNull Context context,int postion) {
        super(context);
        this.postion = postion;
        this.context = context;
        boardItems = boardData.getBoardItem();
        for (BoardItem boardItem :boardItems) {
            Log.d("POSPOS", postion + " /" +boardItem.getTitle() + " /"+ boardItem.getDescription());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_board);
        ButterKnife.bind(this);
        setDialogRating();
        setTxt();
    }
    private void setDialogRating(){
        dialogRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (isFirstTime) {
                    putStar(v);
                }
            }
        });
    }
    private void setTxt(){
        dialogTitle.setText(boardItems[postion].getTitle());
        dialogDescription.setText(boardItems[postion].getDescription());
    }
    private void putStar(float v){
        boolean result = false;
        StarDataLoader dataLoader = new StarDataLoader(context);
        StarsItem starsItem = new StarsItem();
        for(StarsItem item : starsData.getStarsItem()){
            if(item.getHkpk().equals(postion+"")) result = true;
        }

        starsItem.setHkpk(postion+"");
        starsItem.setPoint(v+"");
        starsItem.setCount("1");
        starsItem.setPk("10000");
        dataLoader.postStarsData(starsItem);

    }

}
