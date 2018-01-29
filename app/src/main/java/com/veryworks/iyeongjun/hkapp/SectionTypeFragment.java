package com.veryworks.iyeongjun.hkapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veryworks.iyeongjun.hkapp.EventDriven.RxEventBus;
import com.veryworks.iyeongjun.hkapp.Interface.TypeAndSectionSwapInterface;
import com.veryworks.iyeongjun.hkapp.adapter.TypeAdapter;
import com.veryworks.iyeongjun.hkapp.domain.Const;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.veryworks.iyeongjun.hkapp.domain.StaticFields.isTypeList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SectionTypeFragment extends Fragment {
    @BindView(R.id.typeRecyclerView) RecyclerView typeRecyclerView;
    TypeAndSectionSwapInterface typeAndSectionSwapInterface;
    Unbinder unbinder;

    public SectionTypeFragment() {

    }
    public void setTypeAndSectionSwapInterface(Context context){
        typeAndSectionSwapInterface =
                (TypeAndSectionSwapInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section, container, false);
        unbinder = ButterKnife.bind(this, view);
        setRecycler();
        RxEventBus
                .getInstance()
                .getObservable()
                .subscribe(num -> {if(num == Const.FRAGMENT.SECTION_AND_TYPE) {fabClicked(num);}});
        return view;
    }

    private void fabClicked(int num) {
        if(num == Const.FRAGMENT.SECTION_AND_TYPE){
            if(isTypeList){
                isTypeList = false;
                typeAndSectionSwapInterface.setSectionList();
                setRecycler();
            }else{
                isTypeList = true;
                typeAndSectionSwapInterface.setTypeList();
                setRecycler();
            }
        }
    }
    private void setRecycler() {
        TypeAdapter adapter = new TypeAdapter(getActivity());
        typeRecyclerView.setAdapter(adapter);
        typeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @OnTouch(R.id.btnSwapToSection)
//    public boolean btnSwapToSectionTouched(MotionEvent event){
//        if(event.getAction() == MotionEvent.ACTION_DOWN){
//            if(toggleTypeList) btnSwapToSection.setImageResource(R.drawable.swap_c_type_copy);
//            else btnSwapToSection.setImageResource(R.drawable.swap_c_location);
//        }else if (event.getAction() == MotionEvent.ACTION_UP){
//            if(toggleTypeList) {
//                toggleTypeList = false;
//                btnSwapToSection.setImageResource(R.drawable.swap_type);
//            }else {
//                toggleTypeList = true;
//                btnSwapToSection.setImageResource(R.drawable.swap_location);
//            }
//            setRecycler();
//        }
//        return false;
//    }
}
