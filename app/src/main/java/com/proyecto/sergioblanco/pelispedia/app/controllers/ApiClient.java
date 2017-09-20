package com.proyecto.sergioblanco.pelispedia.app.controllers;

import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sergio Blanco on 28/08/2017.
 */

public class ApiClient {

    //create retrofit instance
    private static Retrofit retrofit = null;


    /**
     * Method to create Retrofit Obejct
     */
    public static Retrofit getClient() {

        //Create client
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).addInterceptor(interceptor).build();

        //Create object
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.MOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
