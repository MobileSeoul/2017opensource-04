package com.veryworks.iyeongjun.hkapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.veryworks.iyeongjun.hkapp.DetailActivity;
import com.veryworks.iyeongjun.hkapp.Interface.SetBackgroundListFragment;
import com.veryworks.iyeongjun.hkapp.R;
import com.veryworks.iyeongjun.hkapp.domain.HKData;
import com.veryworks.iyeongjun.hkapp.domain.Items;
import com.veryworks.iyeongjun.hkapp.domain.StarsItem;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.veryworks.iyeongjun.hkapp.Util.DataConverter.convertSectionIntToString;
import static com.veryworks.iyeongjun.hkapp.Util.DataConverter.convertSectionStringToInt;
import static com.veryworks.iyeongjun.hkapp.Util.DataConverter.convertTypeIntToString;
import static com.veryworks.iyeongjun.hkapp.Util.DataConverter.convertTypeStringToInt;
import static com.veryworks.iyeongjun.hkapp.Util.UserLocation.currentUserLocation;
import static com.veryworks.iyeongjun.hkapp.domain.StaticDrawble.starsImage;
import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.starsData;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Items[] data;
    Context context;
    ArrayList<Items> datas = new ArrayList();
    StarsItem[] mStarsDatas;
    SetBackgroundListFragment setBackInterface;


    public ListAdapter(Context context, SetBackgroundListFragment setBackInterface, HKData hkData) {
        this.context = context;
        this.setBackInterface = setBackInterface;
        data = hkData.getItems();
        datas = new ArrayList<>(Arrays.asList(data));
        mStarsDatas = starsData.getStarsItem();
    }


    public ListAdapter(Context context, SetBackgroundListFragment setBackInterface, HKData hkData, String setting, String type) {
        this.context = context;
        this.setBackInterface = setBackInterface;
        data = hkData.getItems();
        datas = new ArrayList<>(Arrays.asList(data));
        mStarsDatas = starsData.getStarsItem();
        setData(setting, type);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sety, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        for (StarsItem starsItem : mStarsDatas) {
            if (Integer.parseInt(datas.get(position).getPk()) == Integer.parseInt(starsItem.getHkpk())) holder.star = Double.parseDouble(starsItem.getPoint());
        }
        holder.setPostion(position);
        holder.setTxt(datas.get(position));
        holder.setImage(datas.get(position));
        holder.setImgStar();

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private void setData(String setting, String content) {
        ArrayList<Items> list = new ArrayList<>();
        for (Items item : datas) {
            if (setting.equals("section")) {
                if (item.getSection().equals(convertSectionStringToInt(content) + ""))
                    list.add(item);

            } else if (setting.equals("type")) {
                if (item.getType().equals(convertTypeStringToInt(content) + "")) list.add(item);
            }
        }
        datas = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.roundedImageView) RoundedImageView roundedImageView;
        @BindView(R.id.txtTitle) TextView txtTitle;
        @BindView(R.id.txtSection) TextView txtSection;
        @BindView(R.id.txtType) TextView txtType;
        @BindView(R.id.imgStar)ImageView imgStar;
        @BindView(R.id.txtDistance) TextView txtDistance;
        @BindView(R.id.listContainer) CardView listContainer;

        int posistion;
        double star;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setPostion(int postion) {
            this.posistion = postion;
        }

        public void setTxt(Items items) {
            txtTitle.setText("  "+items.getTitle());
            txtSection.setText("  {fa-map-marker} "+convertSectionIntToString(Integer.parseInt(items.getSection()))+" |");
            txtType.setText(" {fa-th-large} "+convertTypeIntToString(Integer.parseInt(items.getType())));
            txtDistance.setText("{fa-arrows-v} "+getDistance(makeLocation(items))+"  ");
        }

        public void setImgStar() {
            if(star >= 0.0 && star <0.5) imgStar.setImageResource(starsImage[0]);
            else if(star >=0.5 && star <1.0) imgStar.setImageResource(starsImage[1]);
            else if(star >=1.0 && star <1.5) imgStar.setImageResource(starsImage[2]);
            else if(star >=1.5 && star <2.0) imgStar.setImageResource(starsImage[3]);
            else if(star >=2.0 && star <2.5) imgStar.setImageResource(starsImage[4]);
            else if(star >=2.5 && star <3.0) imgStar.setImageResource(starsImage[5]);
            else if(star >=3.0 && star <3.5) imgStar.setImageResource(starsImage[6]);
            else if(star >=3.5 && star <4.0) imgStar.setImageResource(starsImage[7]);
            else if(star >=4.0 && star <4.5) imgStar.setImageResource(starsImage[8]);
            else if(star >=4.5 && star <5.0) imgStar.setImageResource(starsImage[9]);
            else if(star >=5.0) imgStar.setImageResource(starsImage[10]);
            else imgStar.setImageResource(starsImage[0]);
        }

        @OnClick(R.id.listContainer)
        public void setClickListener() {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("dataPk", datas.get(posistion).getPk());
            Log.d("TEMP", datas.get(posistion).getPk());
            intent.putExtra("type", "list");
            context.startActivity(intent);
        }

        public void setImage(Items items) {
            Glide
                    .with(context)
                    .load(items.getImage())
                    .into(roundedImageView);

//            if (posistion % 2 == 0) setBackInterface.setBackground(items.getImage());
        }

        private Location makeLocation(Items item) {
            Location location = new Location("Location");
            location.setLatitude(Double.parseDouble(item.getLat()));
            location.setLongitude(Double.parseDouble(item.getLon()));
            location.setAltitude(0.0);
            return location;
        }

        private String getDistance(Location location) {
            double distance = ((int) (currentUserLocation.distanceTo(location))) / (1000.0);
            return distance + "km";
        }
    }
}
