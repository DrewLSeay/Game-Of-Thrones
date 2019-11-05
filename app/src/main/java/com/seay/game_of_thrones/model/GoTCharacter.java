package com.seay.game_of_thrones.model;

public class GoTCharacter {

    private int id;
    private String character;
    private String actor;
    private String description;
    private String image;
    private String caption;
    private String credit;

    public int getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }

    public String getActor() {
        return actor;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getCaption() {
        return caption;
    }

    public String getCredit() {
        return credit;
    }

    public String getCharacterInformation(){
        return character + " is played by " + actor + " and is about " +description;
    }
}
