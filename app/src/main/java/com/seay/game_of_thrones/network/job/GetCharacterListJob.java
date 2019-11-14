package com.seay.game_of_thrones.network.job;

import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.CancelReason;
import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.seay.game_of_thrones.database.MainDAO;
import com.seay.game_of_thrones.database.realmObjects.CharacterInformation;
import com.seay.game_of_thrones.inject.AppComponent;
import com.seay.game_of_thrones.network.dto.CharacterDTO;
import com.seay.game_of_thrones.network.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import timber.log.Timber;

public class GetCharacterListJob extends Job {

    @Inject
    transient ApiService apiService;

    @Inject
    transient MainDAO mainDAO;

    //todo: Define priority in BaseJob
    public static final int PRIORITY = 1;

    public GetCharacterListJob() {
        super(new Params(PRIORITY).requireNetwork());
    }

    @Override
    public void onAdded() {
        // Job has been saved to disk.
        // Could dispatch a loading UI element
        Timber.v("Added %s job.", getClass().getSimpleName());
    }

    @Override
    public void onRun() throws Throwable {
        Timber.v("Starting %s job", getClass().getSimpleName());

        Response<List<CharacterDTO>> response = apiService.getGoTCharactersSynchronously();

        ArrayList<CharacterInformation> characterList = new ArrayList<>();

        for (CharacterDTO characterDTO : response.body()) {
            CharacterInformation characterInformation = new CharacterInformation(characterDTO);
            characterList.add(characterInformation);
        }
        mainDAO.save(characterList);
    }// end onRun()

    @Override
    protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount, int maxRunCount) {
        return RetryConstraint.createExponentialBackoff(runCount, 1000);
    }

    @Override
    protected void onCancel(@CancelReason int cancelReason, @Nullable Throwable throwable) {
        //Job has rerun too many times and is canceled
    }

    public void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }


}
