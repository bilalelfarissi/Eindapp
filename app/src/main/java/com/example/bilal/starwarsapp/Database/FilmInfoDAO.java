package com.example.bilal.starwarsapp.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.bilal.starwarsapp.FilmInfo;;
import java.util.List;

@Dao
public interface FilmInfoDAO {

    @Query("SELECT DISTINCT * FROM film_db")
    public List<FilmInfo> getAllFilm();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(FilmInfo film);

    @Delete
    public void delete(FilmInfo film);

    @Delete
    public void deleteAll(List<FilmInfo> film);

    @Update
    public void update(FilmInfo film);
}
