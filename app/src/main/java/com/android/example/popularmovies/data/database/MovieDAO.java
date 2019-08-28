package com.android.example.popularmovies.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieDAO {
    @Query("SELECT * FROM movie ORDER BY id")
    List<MovieEntry> loadMovies();

    @Insert
    void insertMovie(MovieEntry movieEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieEntry movieEntry);

    @Delete
    void deleteMovie(MovieEntry movieEntry);

    @Query("SELECT * FROM movie WHERE id=:id")
    MovieEntry getMovie(int id);
}
