package com.seay.game_of_thrones.database.realmObjects;

import androidx.annotation.NonNull;

import com.seay.game_of_thrones.network.dto.CharacterDTO;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CharacterInformation extends RealmObject {

    @PrimaryKey
    private long id;

    private String character;

    private String actor;

    private String description;

    private String image;

    private String caption;

    private String credit;

    public CharacterInformation() {
        //Needed for Realm
    }

    public CharacterInformation(@NonNull CharacterDTO result) {
        this.id = result.id;
        this.character = result.character;
        this.actor = result.actor;
        this.description = result.description;
        this.image = result.image;
        this.caption = result.caption;
        this.credit = result.credit;
    }

    public long getId() {
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


}
