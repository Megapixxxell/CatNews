package com.example.domain.service;

import com.example.domain.model.Article;
import com.example.domain.repository.INewsDBRepository;
import com.example.domain.repository.INewsServerRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsServiceImpl implements NewsService {

    @Inject
    INewsDBRepository mDBRepository;

    @Inject
    INewsServerRepository mServerRepository;

    @Inject
    NewsServiceImpl() {
    }

    @Override
    public Single<List<Article>> getNewsFromServer(int page, int pageSize) {
        return mServerRepository.getNews(page, pageSize)
                .doOnSuccess(newsList -> {
                    if (page == 1) {
                        clearCache();
                    }
                    saveToCache(newsList);
                });
    }

    private void clearCache() {
        mDBRepository.clearTable();
    }

    private void saveToCache(List<Article> articleList) {
        mDBRepository.insertNews(articleList);
    }

    @Override
    public List<Article> getNewsFromDB() {
        return mDBRepository.getNews();
    }
}
