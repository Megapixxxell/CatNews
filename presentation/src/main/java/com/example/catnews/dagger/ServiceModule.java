package com.example.catnews.dagger;

import com.example.domain.service.NewsService;
import com.example.domain.service.NewsServiceImpl;

import dagger.Module;
import dagger.Provides;

@Module
class ServiceModule {

    @Provides
    NewsService provideNewsService(NewsServiceImpl service) {
        return service;
    }

}
