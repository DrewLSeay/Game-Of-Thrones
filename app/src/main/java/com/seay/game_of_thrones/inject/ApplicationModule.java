package com.seay.game_of_thrones.inject;

import android.content.Context;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.seay.game_of_thrones.GameOfThronesApplication;
import com.seay.game_of_thrones.database.MainDAO;
import com.seay.game_of_thrones.model.Welcome;
import com.seay.game_of_thrones.network.job.GetCharacterListJob;
import com.seay.game_of_thrones.network.service.ApiService;
import com.seay.game_of_thrones.repository.CharacterRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private GameOfThronesApplication application;

    public ApplicationModule(GameOfThronesApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    Welcome provideWelcome() {
        return new Welcome();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Context context) {
        return new ApiService(context);
    }

    @Provides
    @Singleton
    MainDAO provideAccessMainDAO() {
        return new MainDAO();
    }

    @Provides
    @Singleton
    CharacterRepository provideCharacterRepository() {
        return new CharacterRepository();
    }

    @Provides
    @Singleton
    JobManager jobManager() {
        int maxCount = 5;
        int loadFactor = 3;
        int minCount = 1;

        Configuration configuration = new Configuration.Builder(application)
                .consumerKeepAlive(45)
                .maxConsumerCount(maxCount)
                .loadFactor(loadFactor)
                .minConsumerCount(minCount)
                .injector(job -> {
                    if (job instanceof GetCharacterListJob) {
                        ((GetCharacterListJob) job).inject(Injector.obtain());
                    }
                })
                .build();
        return new JobManager(configuration);
    }
}
