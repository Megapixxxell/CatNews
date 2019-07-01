package com.example.catnews.ui.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.catnews.R;
import com.example.catnews.utils.DateUtils;
import com.example.domain.model.Article;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_news_image)
    ImageView ivImage;
    @BindView(R.id.tv_news_publishedAt)
    TextView tvPublishedAT;
    @BindView(R.id.tv_news_title)
    TextView tvTitle;
    @BindView(R.id.tv_news_desc)
    TextView tvDesc;

    private NewsHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(Article article, NewsAdapter.OnItemClickListener listener) {
        if (article.getUrlToImage() != null) {
            Glide.with(itemView.getContext())
                    .load(article.getUrlToImage())
                    .centerCrop()
                    .into(ivImage);
        } else {
            Glide.with(itemView.getContext())
                    .load(R.drawable.cat_for_splash)
                    .into(ivImage);
        }
        tvPublishedAT.setText(DateUtils.formatDate(article.getPublishedAt()));
        tvTitle.setText(article.getTitle());
        tvDesc.setText(article.getDescription());

        itemView.setOnClickListener(l -> listener.onClick(article.getUrl()));
    }

    public static NewsHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.li_news, parent, false);
        return new NewsHolder(view);
    }
}
