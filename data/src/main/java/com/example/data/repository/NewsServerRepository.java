package com.example.data.repository;

import com.example.data.api.NewsApi;
import com.example.domain.model.Article;
import com.example.domain.model.NewsResponse;
import com.example.domain.repository.INewsServerRepository;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsServerRepository implements INewsServerRepository {

    @Inject
    NewsApi mNewsApi;

    @Inject
    NewsServerRepository() {
    }

    @Override
    public Single<List<Article>> getNews(int page, int pageSize) {
        if (page > 5) return Single.just(Collections.emptyList());
        return mNewsApi.getNews("android",
                "26eddb253e7840f988aec61f2ece2907",
                "2019-06-28",
                "publishedAt",
                pageSize, page)
                .map(NewsResponse::getArticles);
    }
}
