package com.example.catnews;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.catnews.utils.NetworkState;
import com.example.domain.model.Article;
import com.example.domain.service.NewsService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class NewsDataSource extends PageKeyedDataSource<Integer, Article> {

    private NewsService mNewsService = AppDelegate.getAppComponent().getNewsService();

    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    private CompositeDisposable mDisposable;

    private Completable retryCompletable;

    NewsDataSource(CompositeDisposable disposable) {
        mDisposable = disposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Article> callback) {
        networkState.postValue(NetworkState.error(String.valueOf(R.string.check_internet)));
        mDisposable.add(mNewsService.getNewsFromServer(1, params.requestedLoadSize)
                .subscribe(response -> {
                            setRetry(null);
                            networkState.postValue(NetworkState.LOADING);
                            callback.onResult(response, null, 2);
                        },
                        throwable -> {
                            setRetry(() -> loadInitial(params, callback));
                            List<Article> articles = new ArrayList<>(mNewsService.getNewsFromDB());
                            if (articles.size() != 0) {
                                callback.onResult(mNewsService.getNewsFromDB(), null, 6);
                            } else {
                                NetworkState error = NetworkState.error(throwable.getMessage());
                                networkState.postValue(error);
                            }
                        }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        //do nothing
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        networkState.postValue(NetworkState.LOADING);

        mDisposable.add(mNewsService.getNewsFromServer(params.key, params.requestedLoadSize)
                .subscribe(response -> {
                    setRetry(null);
                    networkState.postValue(NetworkState.LOADED);
                    callback.onResult(response, params.key + 1);
                }, throwable -> {
                    if(params.key < 5) {
                        setRetry(() -> loadAfter(params, callback));
                    } else setRetry(null);
                    NetworkState error = NetworkState.error(throwable.getMessage());
                    networkState.postValue(error);
                }));
    }

    private void setRetry(final Action action) {
        if (action == null) {
            this.retryCompletable = null;
        } else {
            this.retryCompletable = Completable.fromAction(action);
        }
    }

    public void retry() {
        if (retryCompletable != null) {
            mDisposable.add(retryCompletable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                    }, Throwable::printStackTrace));
        }
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
