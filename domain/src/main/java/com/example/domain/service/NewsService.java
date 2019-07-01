package com.example.domain.service;


import com.example.domain.model.Article;

import java.util.List;

import io.reactivex.Single;

public interface NewsService {

    Single<List<Article>> getNewsFromServer(int page, int pageSize);
    List<Article> getNewsFromDB();
}
