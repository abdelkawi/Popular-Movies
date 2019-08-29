package com.android.example.popularmovies.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.database.AppDatabase;
import com.android.example.popularmovies.data.database.MovieEntry;
import com.android.example.popularmovies.ui.Interfaces.IMovieClicked;
import com.android.example.popularmovies.ui.adapters.MovieItemAdapter;
import com.android.example.popularmovies.utilities.NetworkUtils;
import com.android.example.popularmovies.viewmodel.MoviesViewModel;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMovieClicked {


    static String sCurrentSortBy = NetworkUtils.POPULAR_END_POINT;
    private TextView mErrorMsgTv;
    private RecyclerView mResultRv;
    private Button mRetryButton;
    private ProgressBar mLoadingPb;
    final String CURRENT_LIST_POSITION = "list_pos";
    private MovieItemAdapter mMovieItemAdapter;
    private MoviesViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        mRetryButton.setOnClickListener(this);

    }

    void setupView() {
        mErrorMsgTv = findViewById(R.id.tv_error_message_display);
        mResultRv = findViewById(R.id.rv_moives_list);
        mRetryButton = findViewById(R.id.retry_button);
        mResultRv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        mLoadingPb = findViewById(R.id.pb_loading_indicator);
        mMovieItemAdapter = new MovieItemAdapter(this);
        mResultRv.setAdapter(mMovieItemAdapter);
        mainViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        showLoading();
        if (sCurrentSortBy != NetworkUtils.FAVORITES)
           mainViewModel.loadMovies(sCurrentSortBy);
        else loadFavorites();
        mainViewModel.getmMoivesList().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(List<MovieEntry> movieEntries) {
                mMovieItemAdapter.setMovieItems(movieEntries);
                showData();
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        if (sCurrentSortBy .equals( NetworkUtils.POPULAR_END_POINT)) {
            menu.findItem(R.id.most_popular).setVisible(false);
        }
        if (sCurrentSortBy .equals( NetworkUtils.TOP_RATED_END_POINT))
            menu.findItem(R.id.top_rated).setVisible(false);
        if (sCurrentSortBy .equals( NetworkUtils.FAVORITES))
            menu.findItem(R.id.my_fav).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        showLoading();
        if (itemThatWasClickedId == R.id.most_popular) {
            sCurrentSortBy = NetworkUtils.POPULAR_END_POINT;
            mainViewModel.loadMovies(sCurrentSortBy);
        } else if (itemThatWasClickedId == R.id.top_rated) {
            sCurrentSortBy = NetworkUtils.TOP_RATED_END_POINT;
            mainViewModel.loadMovies(sCurrentSortBy);
        } else if (itemThatWasClickedId == R.id.my_fav) {
            sCurrentSortBy = NetworkUtils.FAVORITES;
            loadFavorites();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retry_button:
                if (sCurrentSortBy != NetworkUtils.FAVORITES)
                    mainViewModel.loadMovies(sCurrentSortBy);
                else loadFavorites();
                break;
        }
    }

    @Override
    public void onMovieClicked(MovieEntry movieItem) {
        Intent detailIntent = new Intent(this, MovieDetailsActivity.class);
        Gson gson = new Gson();
        String stringMovieObject = gson.toJson(movieItem, MovieEntry.class);
        detailIntent.putExtra("movieItem", stringMovieObject);
        startActivity(detailIntent);
    }
    void showEmpty(){
        mErrorMsgTv.setVisibility(View.VISIBLE);
        mErrorMsgTv.setText(getString(R.string.empty_message));
        mResultRv.setVisibility(View.GONE);
        mLoadingPb.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.VISIBLE);
        mRetryButton.setText(getString(R.string.load_movies));
        sCurrentSortBy=NetworkUtils.POPULAR_END_POINT;

    }
    void showData() {
        mLoadingPb.setVisibility(View.GONE);
        mErrorMsgTv.setVisibility(View.GONE);
        mResultRv.setVisibility(View.VISIBLE);
        mRetryButton.setVisibility(View.GONE);
    }

    void showError() {
        mErrorMsgTv.setVisibility(View.VISIBLE);
        mResultRv.setVisibility(View.GONE);
        mLoadingPb.setVisibility(View.GONE);
        mRetryButton.setVisibility(View.VISIBLE);
    }

    void showLoading() {
        mErrorMsgTv.setVisibility(View.GONE);
        mResultRv.setVisibility(View.GONE);
        mLoadingPb.setVisibility(View.VISIBLE);
        mRetryButton.setVisibility(View.GONE);
    }

    void loadFavorites() {
        showData();
        mainViewModel.getFavorites().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged( List<MovieEntry> movieEntries) {
                if(movieEntries!=null&&movieEntries.size()>0) {
                    mMovieItemAdapter.setMovieItems(movieEntries);
                }else {
                    showEmpty();
                }
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_LIST_POSITION, mResultRv.getLayoutManager().onSaveInstanceState());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mResultRv.getLayoutManager().onRestoreInstanceState(savedInstanceState);
    }
}
