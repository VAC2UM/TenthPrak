package com.itproger.tenthprak.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Films")
public class Film {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "film_id")
    int id;

    @ColumnInfo(name = "Name")
    String filmName;

    @ColumnInfo(name = "Year")
    String filmYear;

    public Film(String filmName, String filmYear) {
        this.id = 0;
        this.filmName = filmName;
        this.filmYear = filmYear;
    }

    @NonNull
    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(@NonNull String filmName) {
        this.filmName = filmName;
    }

    public String getFilmYear() {
        return filmYear;
    }

    public void setFilmYear(String filmYear) {
        this.filmYear = filmYear;
    }
}
