package com.android.example.popularmovies.data;

import com.google.gson.annotations.SerializedName;

public class ReviewItem{

	@SerializedName("media_title")
	private String mediaTitle;

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("author")
	private String author;

	@SerializedName("media_id")
	private int mediaId;

	@SerializedName("id")
	private String id;

	@SerializedName("iso_639_1")
	private String iso6391;

	@SerializedName("content")
	private String content;

	@SerializedName("url")
	private String url;

	public void setMediaTitle(String mediaTitle){
		this.mediaTitle = mediaTitle;
	}

	public String getMediaTitle(){
		return mediaTitle;
	}

	public void setMediaType(String mediaType){
		this.mediaType = mediaType;
	}

	public String getMediaType(){
		return mediaType;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return author;
	}

	public void setMediaId(int mediaId){
		this.mediaId = mediaId;
	}

	public int getMediaId(){
		return mediaId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIso6391(String iso6391){
		this.iso6391 = iso6391;
	}

	public String getIso6391(){
		return iso6391;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"ReviewItem{" + 
			"media_title = '" + mediaTitle + '\'' + 
			",media_type = '" + mediaType + '\'' + 
			",author = '" + author + '\'' + 
			",media_id = '" + mediaId + '\'' + 
			",id = '" + id + '\'' + 
			",iso_639_1 = '" + iso6391 + '\'' + 
			",content = '" + content + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}