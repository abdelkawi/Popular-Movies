/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.example.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String BASE_URL =
            "http://api.themoviedb.org/3/movie/";
    final public static String BASE_IMAGE_URL="http://image.tmdb.org/t/p/w780";
    //TODO add your api_key to test
    public static final String API_KEY="b70de8a7e7d449bca9758c31b3a11ab5";
    public static final  String POPULAR_END_POINT = "popular";
    public static final String TOP_RATED_END_POINT = "top_rated";
    public static final String FAVORITES = "favorites";
    public static Retrofit getClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}