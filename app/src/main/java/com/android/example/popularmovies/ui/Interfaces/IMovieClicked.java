package com.android.example.popularmovies.ui.Interfaces;

import com.android.example.popularmovies.data.database.MovieEntry;

/**
 * Created by kawi on 8/2/2019.
 */

public interface IMovieClicked {
    void onMovieClicked(MovieEntry movieItem);
}
