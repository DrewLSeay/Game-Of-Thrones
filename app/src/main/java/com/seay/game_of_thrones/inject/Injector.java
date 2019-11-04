package com.seay.game_of_thrones.inject;

import androidx.annotation.NonNull;

import com.seay.game_of_thrones.GameOfThronesApplication;

public final class Injector {

    private static AppComponent appComponent;

    private Injector(){
    }

    /**
     * Should be called in onCreate of Application. Dagger won't work until this is called.
     *
     * @param context The Application.
     */
    public static void init(@NonNull GameOfThronesApplication context) {
        appComponent = DaggerAppComponent.builder().applicationModule(new ApplicationModule(context)).build();
    }

    @NonNull
    public static AppComponent obtain() {
        if (appComponent == null) {
            throw (new IllegalStateException("Dagger was not set up correctly. Must call init(context)."));
        } else {
            return appComponent;
        }
    }

}
