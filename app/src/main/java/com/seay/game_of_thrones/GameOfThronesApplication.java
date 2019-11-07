package com.seay.game_of_thrones;

import android.app.Application;

import com.seay.game_of_thrones.inject.Injector;

import timber.log.Timber;

public class GameOfThronesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        setUpDagger();

        setUpLogging();
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
