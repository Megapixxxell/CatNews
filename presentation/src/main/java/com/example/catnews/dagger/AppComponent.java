package com.example.catnews.dagger;

import com.example.catnews.ui.news.NewsActivity;
import com.example.domain.service.NewsService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, RepositoryModule.class, ServiceModule.class})
public interface AppComponent {

    void injectNewsActivity(NewsActivity activity);
    NewsService getNewsService();

}
