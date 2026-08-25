package com.visionsystems.linguaquiz.app;

import android.app.Application;

import com.visionsystems.linguaquiz.data.local.LocalStroge;
import com.visionsystems.linguaquiz.data.repository.AppRepositoryImpl;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalStroge.init(this);
        AppRepositoryImpl.init();
    }
}
