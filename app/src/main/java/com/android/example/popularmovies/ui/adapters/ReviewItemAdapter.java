package com.android.example.popularmovies.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.popularmovies.R;
import com.android.example.popularmovies.data.ReviewItem;

import java.util.ArrayList;
import java.util.List;

public class ReviewItemAdapter  extends RecyclerView.Adapter<ReviewItemAdapter.ReviewItemViewHolder>{
    List<ReviewItem> reviewItems= new ArrayList<>();

    public void setReviewItems(List<ReviewItem> reviewItems) {
        this.reviewItems = reviewItems;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ReviewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user_review,parent,false);
        return new ReviewItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewItemViewHolder holder, int position) {
        holder.mReviewText.setText(reviewItems.get(position).getContent());
        holder.mUserName.setText(reviewItems.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }

    class ReviewItemViewHolder extends RecyclerView.ViewHolder{
        TextView mUserName,mReviewText ;
        public ReviewItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.tv_user_name_display);
            mReviewText = itemView.findViewById(R.id.tv_review_display);
        }
    }
}
