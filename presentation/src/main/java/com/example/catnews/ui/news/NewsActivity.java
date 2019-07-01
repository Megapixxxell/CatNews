package com.example.catnews.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.catnews.AppDelegate;
import com.example.catnews.R;
import com.example.catnews.ui.WebActivity;
import com.example.catnews.utils.NetworkState;
import com.example.domain.model.Article;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

import static com.example.catnews.utils.NetworkState.Status.FAILED;

public class NewsActivity extends MvpAppCompatActivity implements NewsView, NewsAdapter.OnItemClickListener, RetryCallback {

    @BindView(R.id.news_recycler)
    RecyclerView mRecyclerNews;

    @BindView(R.id.refresher)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.tv_error_text)
    TextView mTvError;

    @BindView(R.id.btn_retry)
    Button btnRetry;

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    @InjectPresenter
    NewsPresenter mNewsPresenter;

    private NewsAdapter mNewsAdapter;

    private NetworkState mNetworkState;
    private PagedList<Article> mPagedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_news);

        ButterKnife.bind(this);

        AppDelegate.getAppComponent().injectNewsActivity(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerNews.setLayoutManager(new LinearLayoutManager(this));
        mNewsAdapter = new NewsAdapter(this, this);
        mRecyclerNews.setAdapter(mNewsAdapter);
        mNewsPresenter.getPagedListArticleLiveData().observe(this, pagedList -> {
            mNewsAdapter.submitList(pagedList);
            mPagedList = pagedList;
            mNewsPresenter.checkError(mNetworkState);
        });
        mNewsPresenter.getNetworkState().observe(this, networkState -> {
            mNewsAdapter.setNetworkState(networkState);
            mNewsPresenter.checkError(networkState);
            mNetworkState = networkState;
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> mNewsPresenter.refresh());
    }

    @Override
    public void onClick(String url) {
        mNewsPresenter.openWebView(url);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void checkError(NetworkState networkState) {
        if (networkState != null && mPagedList != null) {
            if (networkState.getStatus() == FAILED && mPagedList.size() == 0) {
                mTvError.setVisibility(View.VISIBLE);
                mTvError.setText(R.string.check_internet);
                btnRetry.setVisibility(View.VISIBLE);
            } else {
                mTvError.setVisibility(View.GONE);
                btnRetry.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void openWebView(String url) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @OnClick(R.id.btn_retry)
    void retryButtonClick() {
        mNewsPresenter.refresh();
    }

    @Override
    public void retry() {
        mNewsPresenter.retry();
    }
}
