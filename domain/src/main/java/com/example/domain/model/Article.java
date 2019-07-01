package com.example.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Article {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id")
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String mTitle;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    private String mDescription;

    @Ignore
    @SerializedName("url")
    private String mUrl;

    @ColumnInfo(name = "urlToImage")
    @SerializedName("urlToImage")
    private String mUrlToImage;

    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    private String mPublishedAt;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        mUrlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }
}
