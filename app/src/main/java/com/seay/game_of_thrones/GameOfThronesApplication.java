package com.seay.game_of_thrones;

import android.app.Application;

import com.seay.game_of_thrones.inject.Injector;

public class GameOfThronesApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        setUpDagger();
    }

    private void setUpDagger() {
        Injector.init(this);
    }

}
