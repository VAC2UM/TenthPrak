package com.itproger.tenthprak;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.itproger.tenthprak.db.Film;
import com.itproger.tenthprak.db.FilmDao;
import com.itproger.tenthprak.db.FilmDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    EditText nameEdit;
    EditText yearEdit;
    Button saveButton;
    Button updateButton;
    Button deleteButton;
    Button findButton;

    FilmDatabase filmDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit = findViewById(R.id.filmName);
        yearEdit = findViewById(R.id.filmYear);
        saveButton = findViewById(R.id.saveBtn);
        updateButton = findViewById(R.id.updateBtn);
        deleteButton = findViewById(R.id.deleteBtn);
        findButton = findViewById(R.id.findBtn);

        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        filmDatabase = Room.databaseBuilder(getApplicationContext(), FilmDatabase.class, "FilmDB")
                .fallbackToDestructiveMigration().build();
        //.addCallback(myCallBack).build();

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String year = yearEdit.getText().toString();

                Film f1 = new Film(name, year);
                addFilmInBackground(f1);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String year = yearEdit.getText().toString();
                updateFilmYearInBackground(name, year);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                deleteFilmByNameInBackground(name);
            }
        });

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                searchFilmByNameInBackground(name);
            }
        });
    }

    public void addFilmInBackground(Film film) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                filmDatabase.getFilmDao().addFilm(film);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Film added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void updateFilmYearInBackground(String name, String year) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                filmDatabase.getFilmDao().updateFilmYear(name, year);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Film updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void deleteFilmByNameInBackground(String name) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                filmDatabase.getFilmDao().deleteFilmByName(name);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Film deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void searchFilmByNameInBackground(String name) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Film film = filmDatabase.getFilmDao().findFilmByName(name);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (film != null) {
                            Toast.makeText(MainActivity.this, "Film found", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Film not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}