package com.seay.game_of_thrones.network.service;

import android.content.Context;

import androidx.annotation.NonNull;

import com.seay.game_of_thrones.BuildConfig;
import com.seay.game_of_thrones.R;
import com.seay.game_of_thrones.network.dto.CharacterDTO;
import com.seay.game_of_thrones.network.interceptor.ErrorInterceptor;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private ApiServiceInterface apiServiceInterface;
    private OkHttpClient okHttpClient;

    public ApiService(@NonNull Context context) {
        configApiService(context);
    }

    private Call<List<CharacterDTO>> getGoTCharactersCall() {

        return apiServiceInterface.getCharacterData("custom",
                "TDEFlq8fr6",
                "gameofthrones",
                "json");
    }

    public void getGoTCharacters(@NonNull Callback<List<CharacterDTO>> callback) {
        getGoTCharactersCall().enqueue(callback);
    }

    public Response<List<CharacterDTO>> getGoTCharactersSynchronously() throws IOException {
        return getGoTCharactersCall().execute();
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
