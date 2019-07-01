package com.example.catnews;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.domain.model.Article;

import io.reactivex.disposables.CompositeDisposable;

public class NewsDataSourceFactory extends DataSource.Factory<Integer, Article> {

    private CompositeDisposable mDisposable;

    private MutableLiveData<NewsDataSource> mNewsDataSourceMutableLiveData = new MutableLiveData<>();

    public NewsDataSourceFactory(CompositeDisposable disposable) {
        mDisposable = disposable;
    }

    @NonNull
    @Override
    public DataSource<Integer, Article> create() {
        NewsDataSource newsDataSource = new NewsDataSource(mDisposable);
        mNewsDataSourceMutableLiveData.postValue(newsDataSource);
        return newsDataSource;
    }

    public MutableLiveData<NewsDataSource> getNewsDataSourceMutableLiveData() {
        return mNewsDataSourceMutableLiveData;
    }
}
