package com.veryworks.iyeongjun.hkapp.EventDriven;

/**
 * Created by iyeongjun on 2017. 11. 25..
 */

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
public class RxEventBus {
    private static RxEventBus instance;
    private PublishSubject<Integer> subject;

    public RxEventBus(){
        this.subject = PublishSubject.create();
    }

    public static RxEventBus getInstance(){
        if(instance == null){
            instance = new RxEventBus();
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
