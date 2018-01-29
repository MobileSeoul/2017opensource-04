package com.veryworks.iyeongjun.hkapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veryworks.iyeongjun.hkapp.EventDriven.RxEventBus;
import com.veryworks.iyeongjun.hkapp.adapter.TourAdapter;
import com.veryworks.iyeongjun.hkapp.domain.Const;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class TournamentFragment extends Fragment {


    @BindView(R.id.tourRecycler)
    RecyclerView tourRecycler;
    Unbinder unbinder;

    public TournamentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        unbinder = ButterKnife.bind(this, view);
        setView();
        return view;
    }
    private void setView(){
        tourRecycler.setHasFixedSize(false);

        TourAdapter adapter = new TourAdapter(getActivity());
        tourRecycler.setAdapter(adapter);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, LinearLayoutCompat.VERTICAL);
        tourRecycler.setLayoutManager(manager);
        RxEventBus
                .getInstance()
                .getObservable()
                .subscribe(num -> {
                    if(num == Const.FRAGMENT.TOUNAMENT){
                        Intent intent = new Intent(getActivity(),WriteActivity.class);
                        startActivity(intent);
                    }
                });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
