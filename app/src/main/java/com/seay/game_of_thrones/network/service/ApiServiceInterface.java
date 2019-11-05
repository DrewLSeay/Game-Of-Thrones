package com.seay.game_of_thrones.network.service;


import com.seay.game_of_thrones.model.GoTCharacter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Drew 11/05/19
 */
public interface ApiServiceInterface {

    String ENDPOINT_CHARACTER_LIST = "data";

    //region Queries
    String LIST = "list";
    String TOKEN = "token";
    String TABLE = "table";
    String FORMAT = "format";
    //end region


    @GET(ENDPOINT_CHARACTER_LIST)
    Call<List<GoTCharacter>> getCharacterData(
            @Query(LIST) String list,
            @Query(TOKEN) String token,
            @Query(TABLE) String table,
            @Query(FORMAT) String format
    );

}
