package com.android.example.popularmovies.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MovieViedosApiResponse{

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<TrailerItem> results;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setResults(List<TrailerItem> results){
		this.results = results;
	}

	public List<TrailerItem> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"MovieViedosApiResponse{" + 
			"id = '" + id + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}