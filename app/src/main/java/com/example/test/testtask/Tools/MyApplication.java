package com.example.test.testtask.Tools;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        AndroidThreeTen.init(this);
    }
}
