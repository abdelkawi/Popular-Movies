package com.android.example.popularmovies.ui.activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.ApiResponse;
import com.android.example.popularmovies.data.MovieItem;
import com.android.example.popularmovies.ui.Interfaces.IMovieClicked;
import com.android.example.popularmovies.ui.adapters.MovieItemAdapter;
import com.android.example.popularmovies.utilities.NetworkUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMovieClicked {
    final static String POPULAR_END_POINT = "popular";
    final static String TOP_RATED_END_POINT = "top_rated";
    final static int SORT_BY_POPULAR = 1;
    final static int SORT_BY_RATE = 2;
    static int sCurrentSortBy = SORT_BY_POPULAR;
    private TextView mErrorMsgTv;
    private RecyclerView mResultRv;
    private Button mRetryButton;
    private ProgressBar mLoadingPb;
    final String CURRENT_LIST_POSITION = "list_pos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mErrorMsgTv = findViewById(R.id.tv_error_message_display);
        mResultRv = findViewById(R.id.rv_moives_list);
        mRetryButton = findViewById(R.id.retry_button);
        mResultRv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        mLoadingPb = findViewById(R.id.pb_loading_indicator);
        showLoading();
        loadData(sCurrentSortBy);
        mRetryButton.setOnClickListener(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        if (sCurrentSortBy == SORT_BY_POPULAR) {
            menu.findItem(R.id.most_popular).setVisible(false);
        }
        if (sCurrentSortBy == SORT_BY_RATE)
            menu.findItem(R.id.top_rated).setVisible(false);
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
            sCurrentSortBy = SORT_BY_POPULAR;
            loadData(sCurrentSortBy);
        } else if (itemThatWasClickedId == R.id.top_rated) {
            sCurrentSortBy = SORT_BY_RATE;
            loadData(sCurrentSortBy);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retry_button:
                loadData(sCurrentSortBy);
                break;
        }
    }

    @Override
    public void onMovieClicked(MovieItem movieItem) {
        Intent detailIntent = new Intent(this,MovieDetailsActivity.class);
        Gson gson = new Gson();
        String stringMovieObject =gson.toJson(movieItem, MovieItem.class);
        detailIntent.putExtra("movieItem",stringMovieObject);
        startActivity(detailIntent);
    }

    class GithubQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            ApiResponse apiResponse = gson.fromJson(s, ApiResponse.class);
            if (apiResponse != null && apiResponse.getResults().size() > 0) {
                mResultRv.setAdapter(new MovieItemAdapter(apiResponse.getResults(),MainActivity.this));
                showData();
            } else {
                showError();
            }
        }
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

    void loadData(int sortBy) {
        URL githubSearchUrl = null;
        if (sortBy == SORT_BY_POPULAR) {
            githubSearchUrl = NetworkUtils.buildUrl(POPULAR_END_POINT);
        } else {
            githubSearchUrl = NetworkUtils.buildUrl(TOP_RATED_END_POINT);
        }
        new GithubQueryTask().execute(githubSearchUrl);
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
