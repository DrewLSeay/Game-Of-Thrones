package com.seay.game_of_thrones.network.dto;

import com.google.gson.annotations.SerializedName;

public class CharacterDTO {

    @SerializedName("ID")
    public int id;

    @SerializedName("Character")
    public String character;

    @SerializedName("Actor")
    public String actor;

    @SerializedName("Description")
    public String description;

    @SerializedName("Image")
    public String image;

    @SerializedName("Caption")
    public String caption;

    @SerializedName("Credit")
    public String credit;
}
