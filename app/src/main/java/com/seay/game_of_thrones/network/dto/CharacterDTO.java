package com.seay.game_of_thrones.network.dto;

import com.google.gson.annotations.SerializedName;

public class CharacterDTO {

    @SerializedName("id")
    public int id;

    @SerializedName("character")
    public String character;

    @SerializedName("actor")
    public String actor;

    @SerializedName("description")
    public String description;

    @SerializedName("image")
    public String image;

    @SerializedName("caption")
    public String caption;

    @SerializedName("credit")
    public String credit;
}
