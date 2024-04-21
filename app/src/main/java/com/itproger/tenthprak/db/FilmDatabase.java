package com.itproger.tenthprak.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Film.class}, version = 1)
public abstract class FilmDatabase extends RoomDatabase {
    public abstract FilmDao getFilmDao();
}
