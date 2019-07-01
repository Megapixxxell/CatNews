package com.example.catnews;

import android.app.Application;

import com.example.catnews.dagger.AppComponent;
import com.example.catnews.dagger.AppModule;
import com.example.catnews.dagger.DaggerAppComponent;
import com.example.catnews.dagger.NetworkModule;

public class AppDelegate extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
