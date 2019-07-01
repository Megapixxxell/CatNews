package com.example.data.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.domain.model.Article;

@Database(entities = {Article.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao getNewsDao();
}
