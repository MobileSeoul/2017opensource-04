package com.veryworks.iyeongjun.hkapp.EventDriven;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by iyeongjun on 2017. 11. 25..
 */

public class RxPagerEventBus {
    private static RxPagerEventBus instance;
    private PublishSubject<Integer> subject;

    public RxPagerEventBus(){
        this.subject = PublishSubject.create();
    }

    public static RxPagerEventBus getInstance(){
        if(instance == null){
            instance = new RxPagerEventBus();
        }
        return instance;
    }

    public void sendEvent(int i){
        subject.onNext(i);
    }

    public Observable<Integer> getObservable(){
        return subject;
    }
}
