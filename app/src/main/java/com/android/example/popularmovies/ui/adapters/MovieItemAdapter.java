package com.android.example.popularmovies.ui.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.database.MovieEntry;
import com.android.example.popularmovies.ui.Interfaces.IMovieClicked;
import com.android.example.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kawi on 7/26/2019.
 */

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.MovieItemViewHolder> {
    private List<MovieEntry> movieItems;

    private IMovieClicked iMovieClicked;

    public void setMovieItems(List<MovieEntry> movieItems) {
        this.movieItems = movieItems;
        notifyDataSetChanged();
    }

    public MovieItemAdapter(IMovieClicked iMovieClicked){
        this.iMovieClicked=iMovieClicked;
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_movie,parent,false);
        return new MovieItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder holder, int position) {
        Picasso.get().load(NetworkUtils.BASE_IMAGE_URL+movieItems.get(position).getPosterPath()).into(holder.posterIv);
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

    class MovieItemViewHolder extends RecyclerView.ViewHolder{
        ImageView posterIv;
        public MovieItemViewHolder(View itemView) {
            super(itemView);
            posterIv =itemView.findViewById(R.id.iv_movie_poster);
            posterIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iMovieClicked.onMovieClicked(movieItems.get(getAdapterPosition()));
                }
            });
        }
    }
}
