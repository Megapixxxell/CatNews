package com.example.data.repository;

import com.example.data.database.NewsDao;
import com.example.domain.model.Article;
import com.example.domain.repository.INewsDBRepository;

import java.util.List;

import javax.inject.Inject;

public class NewsDBRepository implements INewsDBRepository {

    @Inject
    NewsDao mNewsDao;

    @Inject
    NewsDBRepository() {
    }

    @Override
    public List<Article> getNews() {
        return mNewsDao.getNews();
    }

    @Override
    public void insertNews(List<Article> news) {
        mNewsDao.insertNews(news);
    }

    @Override
    public void clearTable() {
        mNewsDao.clearTable();
    }
}
