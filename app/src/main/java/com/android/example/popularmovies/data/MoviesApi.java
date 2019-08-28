package com.android.example.popularmovies.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {
    @GET("{sortBy}")
    Call<MoviesApiResponse> getMovies(@Path("sortBy") String sortBy, @Query("api_key") String apiKey);
    @GET("{id}/videos")
    Call<MovieViedosApiResponse> getMovieVideos(@Path("id")int movieId,@Query("api_key") String apiKey);

    @GET("{id}/reviews")
    Call<MovieReviewsApiResponse> getMovieReviews(@Path("id")int movieId, @Query("api_key") String apiKey);
}
