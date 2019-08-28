package com.android.example.popularmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.example.popularmovies.data.MoviesRepository;
import com.android.example.popularmovies.data.TrailerItem;
import com.android.example.popularmovies.data.database.MovieEntry;
import com.android.example.popularmovies.data.ReviewItem;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {
    private MoviesRepository moviesRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository();
    }

    public void loadMovies(String sortBy) {
        moviesRepository.loadMovies(sortBy);
    }

    public void loadMovieReviews(int movieId) {
        moviesRepository.loadMoviesReviews(movieId);
    }

    public void loadMovieTrailers(int movieId) {
        moviesRepository.loadMovieTrailers(movieId);
    }

    public LiveData<List<MovieEntry>> getmMoivesList() {
        return moviesRepository.getMoviesLiveData();
    }

    public LiveData<List<ReviewItem>> getMovieReviews() {
        return moviesRepository.getMoviesReviewsLiveData();
    }
    public LiveData<List<TrailerItem>> getMovieTrailers(){
        return moviesRepository.getMoviesTrailersLiveData();
    }

}
