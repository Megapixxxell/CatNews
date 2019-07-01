package com.example.catnews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.catnews.NewsDataSource;
import com.example.catnews.NewsDataSourceFactory;
import com.example.catnews.utils.NetworkState;
import com.example.domain.model.Article;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.disposables.CompositeDisposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> {


    private Executor mExecutor;

    private CompositeDisposable mDisposable = new CompositeDisposable();

    private LiveData<PagedList<Article>> mPagedListArticleLiveData;

    private static final int PAGE_SIZE = 5;

    private NewsDataSourceFactory mDataSourceFactory;


    NewsPresenter() {
        mExecutor = Executors.newFixedThreadPool(5);
        mDataSourceFactory = new NewsDataSourceFactory(mDisposable);
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setEnablePlaceholders(false)
                .setPrefetchDistance(5)
                .setMaxSize(5*PAGE_SIZE)
                .build();

        mPagedListArticleLiveData = new LivePagedListBuilder<>(mDataSourceFactory, config)
                .setFetchExecutor(mExecutor)
                .build();
    }

    void retry() {
        mDataSourceFactory.getNewsDataSourceMutableLiveData().getValue().retry();
    }

    void refresh() {
        getViewState().showProgress();
        mDataSourceFactory.getNewsDataSourceMutableLiveData().getValue().invalidate();
        getViewState().hideProgress();
    }

    LiveData<NetworkState> getNetworkState() {
        return Transformations.switchMap(mDataSourceFactory.getNewsDataSourceMutableLiveData(), NewsDataSource::getNetworkState);
    }

    LiveData<PagedList<Article>> getPagedListArticleLiveData() {
        return mPagedListArticleLiveData;
    }

    void openWebView(String url) {
        getViewState().openWebView(url);
    }

    void checkError(NetworkState networkState) {
        getViewState().checkError(networkState);
    }

    void dispose() {
        mDisposable.dispose();
    }
}
