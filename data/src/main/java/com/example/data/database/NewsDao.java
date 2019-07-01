package com.example.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.domain.model.Article;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(List<Article> news);

    @Query("select * from article order by publishedAt desc")
    List<Article> getNews();

    @Query("delete from article")
    void clearTable();
}
