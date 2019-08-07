package com.android.example.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.MovieItem;
import com.android.example.popularmovies.utilities.NetworkUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by kawi on 8/2/2019.
 */

public class MovieDetailsActivity extends AppCompatActivity {
    private ImageView mPosterIv ;
    private TextView mTitleTv,mVoteAverage,mReleaseDate,mPlotSynopsis;
    private MovieItem movieItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mPosterIv=findViewById(R.id.iv_movie_poster);
        mReleaseDate=findViewById(R.id.tv_release_date_display);
        mTitleTv=findViewById(R.id.tv_title_display);
        mVoteAverage=findViewById(R.id.tv_vote_average_display);
        mPlotSynopsis=findViewById(R.id.tv_plot_synopsis_display);
        getData();
        updateUI();
    }
    void updateUI(){
        Picasso.get().load(NetworkUtils.BASE_IMAGE_URL+movieItem.getPosterPath()).into(mPosterIv);
        mVoteAverage.setText(movieItem.getVoteAverage()+"");
        mTitleTv.setText(movieItem.getTitle());
        mReleaseDate.setText(movieItem.getReleaseDate());
        mPlotSynopsis.setText(movieItem.getOverview());
    }
    void getData(){
        Intent intent = getIntent();
        String retrievedString = intent.getStringExtra("movieItem");
        Gson gson = new Gson();
        movieItem = gson.fromJson(retrievedString,MovieItem.class);
    }
}
