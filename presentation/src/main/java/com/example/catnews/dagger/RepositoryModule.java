package com.example.catnews.dagger;

import com.example.data.repository.NewsDBRepository;
import com.example.data.repository.NewsServerRepository;
import com.example.domain.repository.INewsDBRepository;
import com.example.domain.repository.INewsServerRepository;

import dagger.Module;
import dagger.Provides;

@Module
class RepositoryModule {

    @Provides
    INewsServerRepository provideNewsServerRepository(NewsServerRepository repository) {
        return repository;
    }

    @Provides
    INewsDBRepository provideNewsDBRepository(NewsDBRepository repository) {
        return repository;
    }
}
