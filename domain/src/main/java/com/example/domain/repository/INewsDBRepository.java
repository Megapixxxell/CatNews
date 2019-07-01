package com.example.domain.repository;

import com.example.domain.model.Article;

import java.util.List;

public interface INewsDBRepository {
    List<Article> getNews();
    void insertNews(List<Article> news);
    void clearTable();

}
