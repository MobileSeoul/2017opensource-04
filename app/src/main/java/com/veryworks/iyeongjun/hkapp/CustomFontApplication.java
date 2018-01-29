package com.veryworks.iyeongjun.hkapp;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.tsengvn.typekit.Typekit;

/**
 * Created by iyeongjun on 2017. 11. 29..
 */

public class CustomFontApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new FontAwesomeModule());
        Typekit.getInstance()
                .addItalic(Typekit.createFromAsset(this,"ythin.ttf"))
                .addNormal(Typekit.createFromAsset(this,"yNomal.ttf"))
                .addBold(Typekit.createFromAsset(this,"yBold.ttf"));
    }
}
