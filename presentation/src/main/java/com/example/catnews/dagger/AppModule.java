package com.example.catnews.dagger;

import androidx.room.Room;

import com.example.catnews.AppDelegate;
import com.example.data.database.NewsDao;
import com.example.data.database.NewsDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final AppDelegate mApp;

    public AppModule(AppDelegate app) {
        mApp = app;
    }

    @Provides
    @Singleton
    AppDelegate provideApp() {return mApp;}

    @Provides
    @Singleton
    NewsDatabase provideDatabase() {
        return Room.databaseBuilder(mApp, NewsDatabase.class, "new database")
                .fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    NewsDao provideNewDao(NewsDatabase newsDatabase) {
        return newsDatabase.getNewsDao();
    }

}
