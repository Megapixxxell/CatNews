package com.example.catnews.ui.news;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catnews.utils.NetworkState;
import com.example.domain.model.Article;

public class NewsAdapter extends PagedListAdapter<Article, RecyclerView.ViewHolder> {

    private static final int NETWORK_STATE = 0;
    private static final int ITEM = 1;

    private final OnItemClickListener mOnItemClickListener;
    private RetryCallback mRetryCallback;
    private NetworkState mNetworkState;

    NewsAdapter(OnItemClickListener onItemClickListener, RetryCallback callback) {
        super(CALLBACK);
        mOnItemClickListener = onItemClickListener;
        mRetryCallback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM:
                return NewsHolder.create(parent);
            case NETWORK_STATE:
                return NetworkStateViewHolder.create(parent);
            default:
                throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                ((NewsHolder) holder).bind(getItem(position), mOnItemClickListener);
                break;
            case NETWORK_STATE:
                ((NetworkStateViewHolder) holder).bind(mNetworkState, mRetryCallback);
                break;
        }
    }

    private boolean hasExtraRow() {
        return mNetworkState != null && mNetworkState != NetworkState.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return NETWORK_STATE;
        } else {
            return ITEM;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        if (getCurrentList() != null) {
            if (getCurrentList().size() != 0) {
                NetworkState previousState = this.mNetworkState;
                boolean hadExtraRow = hasExtraRow();
                this.mNetworkState = newNetworkState;
                boolean hasExtraRow = hasExtraRow();
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount());
                    } else {
                        notifyItemInserted(super.getItemCount());
                    }
                } else if (hasExtraRow && previousState != newNetworkState) {
                    notifyItemChanged(getItemCount() - 1);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    public interface OnItemClickListener {
        void onClick(String url);
    }

    private static final DiffUtil.ItemCallback<Article> CALLBACK = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.equals(newItem);
        }
    };
}
