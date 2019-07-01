package com.example.catnews.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catnews.R;
import com.example.catnews.utils.NetworkState;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkStateViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_error_text)
    TextView mTvError;

    @BindView(R.id.btn_retry)
    Button btnRetry;

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private NetworkStateViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(NetworkState networkState, RetryCallback callback) {
        switch (networkState.getStatus()) {
            case RUNNING:
                pbLoading.setVisibility(View.VISIBLE);
                break;
            case SUCCESS:
                mTvError.setVisibility(View.GONE);
                btnRetry.setVisibility(View.GONE);
                pbLoading.setVisibility(View.GONE);
                break;
            case FAILED:
                mTvError.setVisibility(View.VISIBLE);
                mTvError.setText(R.string.check_internet);
                btnRetry.setVisibility(View.VISIBLE);
                break;
        }
        btnRetry.setOnClickListener(v -> callback.retry());
    }

    public static NetworkStateViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.li_network_state, parent, false);
        return new NetworkStateViewHolder(view);
    }
}
