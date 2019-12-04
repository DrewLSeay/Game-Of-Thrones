package com.seay.game_of_thrones.androidViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.seay.game_of_thrones.database.realmObjects.CharacterInformation;
import com.seay.game_of_thrones.inject.Injector;
import com.seay.game_of_thrones.network.service.ApiService;
import com.seay.game_of_thrones.repository.CharacterRepository;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

public class CharacterAndroidViewModel extends AndroidViewModel {

    @Inject
    CharacterRepository characterRepository;

    @Inject
    ApiService apiService;

    private Realm realm;

    public CharacterAndroidViewModel(@NonNull Application application) {
        super(application);
        Injector.obtain().inject(this);
        realm = Realm.getDefaultInstance();
    }

    public LiveData<List<CharacterInformation>> getCharactersInformation() {
        return characterRepository.getCharacterInformation(realm);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        realm.close();
    }
}
