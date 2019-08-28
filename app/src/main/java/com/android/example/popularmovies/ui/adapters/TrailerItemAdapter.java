package com.android.example.popularmovies.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.ReviewItem;
import com.android.example.popularmovies.data.TrailerItem;
import com.android.example.popularmovies.ui.Interfaces.ITrailerClicked;

import java.util.ArrayList;
import java.util.List;

public class TrailerItemAdapter extends RecyclerView.Adapter<TrailerItemAdapter.TrailerItemViewHolder> {
    List<TrailerItem> trailerItems = new ArrayList<>();
    ITrailerClicked mItrailerClicked;
    public TrailerItemAdapter(ITrailerClicked iTrailerClicked){
        this.mItrailerClicked=iTrailerClicked;
    }
    public void setTrailerItems(List<TrailerItem> trailerItems) {
        this.trailerItems = trailerItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_movie_trailer, parent, false);
        return new TrailerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerItemViewHolder holder, int position) {
        holder.mTrailerTitle.setText(trailerItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return trailerItems.size();
    }

    class TrailerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTrailerTitle;

        public TrailerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrailerTitle = itemView.findViewById(R.id.tv_trailer_title_display);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mItrailerClicked.playVideo(trailerItems.get(getAdapterPosition()).getKey());
        }
    }
}
