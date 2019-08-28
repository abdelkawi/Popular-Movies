package com.android.example.popularmovies.data;

import androidx.lifecycle.MutableLiveData;

import com.android.example.popularmovies.data.database.MovieEntry;
import com.android.example.popularmovies.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {
    private MoviesApi moviesApi;

    public MoviesRepository() {
        moviesApi = NetworkUtils.getClient().create(MoviesApi.class);
    }

    private MutableLiveData<List<MovieEntry>> moviesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ReviewItem>> moviesReviewsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<TrailerItem>> moviesTrailersLiveData=  new MutableLiveData<>();

    public MutableLiveData<List<MovieEntry>> getMoviesLiveData() {
        return moviesLiveData;
    }

    public MutableLiveData<List<ReviewItem>> getMoviesReviewsLiveData() {
        return moviesReviewsLiveData;
    }

    public MutableLiveData<List<TrailerItem>> getMoviesTrailersLiveData() {
        return moviesTrailersLiveData;
    }

    public void loadMovies(String sortBy) {
        moviesApi.getMovies(sortBy, NetworkUtils.API_KEY).enqueue(new Callback<MoviesApiResponse>() {
            @Override
            public void onResponse(Call<MoviesApiResponse> call, Response<MoviesApiResponse> response) {
                moviesLiveData.setValue(response.body().getResults());
            }
            @Override
            public void onFailure(Call<MoviesApiResponse> call, Throwable t) {
                moviesLiveData.setValue(new ArrayList<MovieEntry>());
            }
        });
    }

    public void loadMoviesReviews(int movieId) {
        moviesApi.getMovieReviews(movieId, NetworkUtils.API_KEY).enqueue(new Callback<MovieReviewsApiResponse>() {
            @Override
            public void onResponse(Call<MovieReviewsApiResponse> call, Response<MovieReviewsApiResponse> response) {
                moviesReviewsLiveData.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieReviewsApiResponse> call, Throwable t) {
                moviesReviewsLiveData.setValue(new ArrayList<ReviewItem>());
            }
        });
    }
    public void loadMovieTrailers(int movieId){
        moviesApi.getMovieVideos(movieId,NetworkUtils.API_KEY).enqueue(new Callback<MovieViedosApiResponse>() {
            @Override
            public void onResponse(Call<MovieViedosApiResponse> call, Response<MovieViedosApiResponse> response) {
                moviesTrailersLiveData.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieViedosApiResponse> call, Throwable t) {
                moviesTrailersLiveData.setValue(new ArrayList<TrailerItem>());
            }
        });
    }
}
