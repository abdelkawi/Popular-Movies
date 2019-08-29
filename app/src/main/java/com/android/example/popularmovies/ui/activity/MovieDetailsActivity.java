package com.android.example.popularmovies.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.TrailerItem;
import com.android.example.popularmovies.data.database.AppDatabase;
import com.android.example.popularmovies.data.database.MovieEntry;
import com.android.example.popularmovies.data.ReviewItem;
import com.android.example.popularmovies.ui.Interfaces.ITrailerClicked;
import com.android.example.popularmovies.ui.adapters.ReviewItemAdapter;
import com.android.example.popularmovies.ui.adapters.TrailerItemAdapter;
import com.android.example.popularmovies.utilities.AppExecutors;
import com.android.example.popularmovies.utilities.NetworkUtils;
import com.android.example.popularmovies.viewmodel.MoviesViewModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kawi on 8/2/2019.
 */

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener, ITrailerClicked {
    private ImageView mPosterIv;
    private TextView mTitleTv, mVoteAverage, mReleaseDate, mPlotSynopsis, mMovieTimeTv, mMarkAsFavTV;
    private RecyclerView mReviewsRv,mTrailersRv;
    private MovieEntry mMovieItem;
    private AppDatabase mAppDb;
    private boolean mIsMarked = false;
    private MoviesViewModel moviesViewModel;
    private ReviewItemAdapter mReviewItemAdapter = new ReviewItemAdapter();
    private TrailerItemAdapter mTrailerItemAdapter = new TrailerItemAdapter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initViews();
        getData();
        updateUI();

    }

    void initViews() {
        mPosterIv = findViewById(R.id.iv_movie_poster);
        mReleaseDate = findViewById(R.id.tv_release_date_display);
        mTitleTv = findViewById(R.id.tv_title_display);
        mVoteAverage = findViewById(R.id.tv_vote_average_display);
        mPlotSynopsis = findViewById(R.id.tv_plot_synopsis_display);
        mMovieTimeTv = findViewById(R.id.tv_movie_time_display);
        mMarkAsFavTV = findViewById(R.id.tv_mark_as_fav_action);
        mReviewsRv= findViewById(R.id.rv_reviews_display);
        mTrailersRv =findViewById(R.id.rv_trailers_display);
        mReviewsRv.setLayoutManager(new LinearLayoutManager(this));
        mTrailersRv.setLayoutManager(new LinearLayoutManager(this));
        mReviewsRv.setAdapter(mReviewItemAdapter);
        mTrailersRv.setAdapter(mTrailerItemAdapter);
        mMarkAsFavTV.setOnClickListener(this);
        mAppDb = AppDatabase.getInstance(this);
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

    }

    void updateUI() {
        Picasso.get().load(NetworkUtils.BASE_IMAGE_URL + mMovieItem.getPosterPath()).into(mPosterIv);
        mVoteAverage.setText(mMovieItem.getVoteAverage() + "/10");
        mTitleTv.setText(mMovieItem.getTitle());
        mReleaseDate.setText(mMovieItem.getReleaseDate());
        mPlotSynopsis.setText(mMovieItem.getOverview());
        checkIfFav();
        loadReviews();
        loadTrailers();
    }
    void loadReviews(){
        moviesViewModel.loadMovieReviews(mMovieItem.getId());
        moviesViewModel.getMovieReviews().observe(this, new Observer<List<ReviewItem>>() {
            @Override
            public void onChanged(List<ReviewItem> reviewItems) {
                mReviewItemAdapter.setReviewItems(reviewItems);
            }
        });
    }
    void loadTrailers(){
        moviesViewModel.loadMovieTrailers(mMovieItem.getId());
        moviesViewModel.getMovieTrailers().observe(this, new Observer<List<TrailerItem>>() {
            @Override
            public void onChanged(List<TrailerItem> trailerItems) {
                mTrailerItemAdapter.setTrailerItems(trailerItems);
            }
        });
    }
    void getData() {
        Intent intent = getIntent();
        String retrievedString = intent.getStringExtra("movieItem");
        Gson gson = new Gson();
        mMovieItem = gson.fromJson(retrievedString, MovieEntry.class);
    }

    void checkIfFav() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mAppDb.taskDAO().getMovie(mMovieItem.getId()) != null)
                    mIsMarked = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mIsMarked)
                            mMarkAsFavTV.setText(getString(R.string.unmark_as_fav));
                        else mMarkAsFavTV.setText(getString(R.string.mark_as_fav));

                    }
                });
            }
        });
    }

    void addToFavs() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!mIsMarked) {
                    mAppDb.taskDAO().insertMovie(mMovieItem);
                    mIsMarked=true;
                } else {
                    mAppDb.taskDAO().deleteMovie(mMovieItem);
                    mIsMarked=false;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mark_as_fav_action:
                addToFavs();
                break;
        }
    }

    @Override
    public void playVideo(String videoId) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v="+videoId));
        try {
            startActivity(webIntent);
        } catch (ActivityNotFoundException ex) {
        }
    }
}
