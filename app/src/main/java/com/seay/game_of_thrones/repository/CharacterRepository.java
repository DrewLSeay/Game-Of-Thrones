package com.seay.game_of_thrones.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.birbit.android.jobqueue.JobManager;
import com.seay.game_of_thrones.database.MainDAO;
import com.seay.game_of_thrones.database.realmObjects.CharacterInformation;
import com.seay.game_of_thrones.inject.Injector;
import com.seay.game_of_thrones.network.job.GetCharacterListJob;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

public class CharacterRepository {

    @Inject
    JobManager jobManager;

    @Inject
    MainDAO mainDAO;

    public CharacterRepository() {
        Injector.obtain().inject(this);
    }

    public LiveData<List<CharacterInformation>> getCharacterInformation(@NonNull Realm realm) {
        jobManager.addJobInBackground(new GetCharacterListJob());
        return Transformations.map(mainDAO.findAllCharacterInformation(realm), realm::copyFromRealm);
    }
}
