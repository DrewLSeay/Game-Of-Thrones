package com.seay.game_of_thrones.network.service;

import android.content.Context;

import androidx.annotation.NonNull;


import com.seay.game_of_thrones.model.GoTCharacter;
import com.seay.game_of_thrones.util.Constants;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private ApiServiceInterface apiServiceInterface;
    private OkHttpClient okHttpClient;

    public ApiService(@NonNull Context context) {
        configApiService(context);
    }

    private Call<List<GoTCharacter>> getGoTCharactersCall(String list, String token, String table, String format){
        return apiServiceInterface.getCharacterData(list, token, table, format);
    }

    public void getGoTCharacters(Callback<List<GoTCharacter>> callback){
        getGoTCharactersCall("custom",
                "TDEFlq8fr6",
                "gameofthrones",
                "json")
                .enqueue( callback);
    }

    public void configApiService(@NonNull Context context){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiServiceInterface = retrofit.create(ApiServiceInterface.class);
    }



}
