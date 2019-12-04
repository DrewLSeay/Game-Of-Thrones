package com.seay.game_of_thrones;

import android.app.Application;

import com.seay.game_of_thrones.database.MainDAO;
import com.seay.game_of_thrones.inject.Injector;

import timber.log.Timber;

public class GameOfThronesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        setUpRealm();

        setUpDagger();

        setUpLogging();
    }

    private void setUpRealm() {
        MainDAO.init(this);
    }

    private void setUpDagger() {
        Injector.init(this);
    }

    private void setUpLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            //todo: reporting release tree
        }
    }
}
