package com.example.domain.repository;

import com.example.domain.model.Article;

import java.util.List;

import io.reactivex.Single;

public interface INewsServerRepository {

    Single<List<Article>> getNews(int page, int pageSize);

}
