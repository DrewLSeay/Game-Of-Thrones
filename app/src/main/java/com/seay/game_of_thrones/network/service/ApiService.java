package com.seay.game_of_thrones.network.service;

import android.content.Context;

import androidx.annotation.NonNull;

import com.seay.game_of_thrones.BuildConfig;
import com.seay.game_of_thrones.R;
import com.seay.game_of_thrones.model.CharacterDTO;
import com.seay.game_of_thrones.network.interceptor.ErrorInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

    private Call<List<CharacterDTO>> getGoTCharactersCall(@NonNull String list,
                                                          @NonNull String token,
                                                          @NonNull String table,
                                                          @NonNull String format) {

        return apiServiceInterface.getCharacterData(list, token, table, format);
    }

    public void getGoTCharacters(@NonNull Callback<List<CharacterDTO>> callback) {
        getGoTCharactersCall("custom",
                "TDEFlq8fr6",
                "gameofthrones",
                "json")
                .enqueue(callback);
    }


    private void configApiService(@NonNull Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new ErrorInterceptor(context))
                .addInterceptor(loggingInterceptor);

        okHttpClient = okClientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        apiServiceInterface = retrofit.create(ApiServiceInterface.class);
    }


}
