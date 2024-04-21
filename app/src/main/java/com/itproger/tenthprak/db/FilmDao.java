package com.itproger.tenthprak.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface FilmDao {
    @Insert
    public void addFilm(Film film);

    @Delete
    public void delete(Film film);

    @Update
    public void update(Film film);

    @Query("select * from films")
    public List<Film> getAllFilms();

    @Query("select * from films where film_id ==:film_id")
    public Film getFilm(int film_id);
}
