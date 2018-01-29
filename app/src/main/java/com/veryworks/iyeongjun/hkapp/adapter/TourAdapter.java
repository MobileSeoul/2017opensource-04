package com.veryworks.iyeongjun.hkapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.veryworks.iyeongjun.hkapp.BoardDialog;
import com.veryworks.iyeongjun.hkapp.DetailActivity;
import com.veryworks.iyeongjun.hkapp.R;
import com.veryworks.iyeongjun.hkapp.SignUpDialog;
import com.veryworks.iyeongjun.hkapp.domain.BoardItem;
import com.veryworks.iyeongjun.hkapp.domain.Items;
import com.veryworks.iyeongjun.hkapp.domain.StarsData;
import com.veryworks.iyeongjun.hkapp.domain.StarsItem;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.veryworks.iyeongjun.hkapp.R.id.imgStar;
import static com.veryworks.iyeongjun.hkapp.R.id.toTxtDecription;
import static com.veryworks.iyeongjun.hkapp.Util.UtilMethod.setLimitLength;
import static com.veryworks.iyeongjun.hkapp.domain.StaticDrawble.starsImage;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.boardData;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.starsData;

/**
 * Created by iyeongjun on 2017. 11. 29..
 */

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {


    private ArrayList<BoardItem> datas;
    Context context;
    StarsItem[] mStarsDatas;
    public TourAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>(Arrays.asList(boardData.getBoardItem()));
        for (BoardItem item : boardData.getBoardItem()) {
            Log.d("Tour", item.getTitle());
        }
        for (BoardItem item : datas) {
            Log.d("Tour", item.getTitle());
        }
        mStarsDatas = starsData.getStarsItem();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        for (StarsItem starsItem : mStarsDatas) {
            if (Integer.parseInt(datas.get(position).getPk()) == Integer.parseInt(starsItem.getHkpk())){
                holder.star = Double.parseDouble(starsItem.getPoint());
            }
        }
        holder.setText(datas.get(position));
        holder.setImgStar();
        holder.setImage(datas.get(position));
        holder.setPostion(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.toImage)
        ImageView toImage;
        @BindView(R.id.toTxtTitle)
        TextView toTxtTitle;
        @BindView(R.id.toImgStar)
        ImageView toImgStar;
        @BindView(R.id.toContainer)
        CardView toContainer;
        @BindView(R.id.toTxtDecription)
        TextView toTxtDescription;
        int position;
        double star;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setText(BoardItem boardItem) {
            toTxtTitle.setText(boardItem.getTitle());
            toTxtDescription.setText(setLimitLength(boardItem.getDescription(),40));
        }

        public void setPostion(int position) {
            this.position = position;
        }

        public void setImgStar() {
            if (star >= 0.0 && star < 0.5) toImgStar.setImageResource(starsImage[0]);
            else if (star >= 0.5 && star < 1.0) toImgStar.setImageResource(starsImage[1]);
            else if (star >= 1.0 && star < 1.5) toImgStar.setImageResource(starsImage[2]);
            else if (star >= 1.5 && star < 2.0) toImgStar.setImageResource(starsImage[3]);
            else if (star >= 2.0 && star < 2.5) toImgStar.setImageResource(starsImage[4]);
            else if (star >= 2.5 && star < 3.0) toImgStar.setImageResource(starsImage[5]);
            else if (star >= 3.0 && star < 3.5) toImgStar.setImageResource(starsImage[6]);
            else if (star >= 3.5 && star < 4.0) toImgStar.setImageResource(starsImage[7]);
            else if (star >= 4.0 && star < 4.5) toImgStar.setImageResource(starsImage[8]);
            else if (star >= 4.5 && star < 5.0) toImgStar.setImageResource(starsImage[9]);
            else if (star >= 5.0) toImgStar.setImageResource(starsImage[10]);
            else toImgStar.setImageResource(starsImage[0]);
        }

        @OnClick(R.id.toContainer)
        public void setClickListener() {
            final BoardDialog dialog = new BoardDialog(context,position);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        }

        public void setImage(BoardItem items) {
            Glide
                    .with(context)
                    .load(items.getImage())
                    .into(toImage);

        }
    }
}
