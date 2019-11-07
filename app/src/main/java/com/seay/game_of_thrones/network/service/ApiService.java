package com.seay.game_of_thrones.network.service;

import android.content.Context;

import androidx.annotation.NonNull;

import com.seay.game_of_thrones.R;
import com.seay.game_of_thrones.model.CharacterDTO;

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

    private Call<List<CharacterDTO>> getGoTCharactersCall(@NonNull String list,
                                                          @NonNull String token,
                                                          @NonNull String table,
                                                          @NonNull String format) {

        return apiServiceInterface.getCharacterData(list, token, table, format);
    }

    public void getGoTCharacters(Callback<List<CharacterDTO>> callback) {
        getGoTCharactersCall("custom",
                "TDEFlq8fr6",
                "gameofthrones",
                "json")
                .enqueue(callback);
    }

    private void configApiService(@NonNull Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiServiceInterface = retrofit.create(ApiServiceInterface.class);
    }


}
