package com.itproger.tenthprak;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.itproger.tenthprak.db.Film;
import com.itproger.tenthprak.db.FilmDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    EditText nameEdit;
    EditText yearEdit;
    Button saveButton;

    FilmDatabase filmDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit = findViewById(R.id.filmName);
        yearEdit = findViewById(R.id.filmYear);
        saveButton = findViewById(R.id.saveBtn);

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

        filmDatabase = Room.databaseBuilder(getApplicationContext(), FilmDatabase.class,
                "FilmDB").addCallback(myCallBack).build();

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String year = yearEdit.getText().toString();

                Film f1 = new Film(name, year);
                addFilmInBackground(f1);
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
}