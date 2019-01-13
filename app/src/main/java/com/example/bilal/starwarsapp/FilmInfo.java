package com.example.bilal.starwarsapp;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "film_db")
public class FilmInfo implements Parcelable, Serializable{
//    @PrimaryKey(autoGenerate = true)
//    private long id;

    @ColumnInfo(name= "ratingNumber")
    private String ratingNumber;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name= "filmNaam")
    private String filmNaam;

    @ColumnInfo(name= "filmPicture")
    private String filmPicture;

    @ColumnInfo(name= "filmoverview")
    private String overview;

    public FilmInfo(String ratingNumber, @NonNull String filmNaam, String filmPicture, String overview) {

        this.ratingNumber = ratingNumber;
        this.filmNaam = filmNaam;
        this.filmPicture = filmPicture;
        this.overview = overview;
    }

    public FilmInfo(Parcel in) {
//        id = in.readLong();
        ratingNumber = in.readString();
        filmNaam = in.readString();
        filmPicture = in.readString();
        overview = in.readString();
    }


//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public String getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(String ratingNumber) {
        this.ratingNumber = ratingNumber;
    }

    public String getFilmNaam() {
        return filmNaam;
    }

    public void setFilmNaam(String filmNaam) {
        this.filmNaam = filmNaam;
    }

    public String getFilmPicture() { return filmPicture; }

    public void setFilmPicture(String filmPicture) { this.filmPicture = filmPicture; }

    public void setOverview(String overview) { this.overview = overview; }

    public String getOverview() {return overview; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeLong(this.id);
        parcel.writeString(this.ratingNumber);
        parcel.writeString(this.filmNaam);
        parcel.writeString(this.filmPicture);
        parcel.writeString(this.overview);
        }
    public static final Creator<FilmInfo> CREATOR = new Creator<FilmInfo>() {
        @Override
        public FilmInfo createFromParcel(Parcel in) {
            return new FilmInfo(in);
        }

        @Override
        public FilmInfo[] newArray(int size) {
            return new FilmInfo[size];
        }
    };
}
