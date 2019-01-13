package com.example.bilal.starwarsapp.Database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.bilal.starwarsapp.FilmInfo;


@Database(entities = {FilmInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {


    public abstract FilmInfoDAO filmInfo();

    private final static String NAME_DATABASE = "film_db";


    //Static instance
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {

        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE)
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;

    }

}
