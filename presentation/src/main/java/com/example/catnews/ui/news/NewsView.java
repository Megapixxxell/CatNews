package com.example.catnews.ui.news;

import com.example.catnews.utils.NetworkState;

import moxy.MvpView;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface NewsView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showProgress();
    @StateStrategyType(SkipStrategy.class)
    void hideProgress();
    void checkError(NetworkState networkState);
    @StateStrategyType(SkipStrategy.class)
    void openWebView(String url);
}
