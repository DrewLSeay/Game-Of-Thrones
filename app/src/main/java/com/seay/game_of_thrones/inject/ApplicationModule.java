package com.seay.game_of_thrones.inject;

import android.content.Context;

import com.seay.game_of_thrones.GameOfThronesApplication;
import com.seay.game_of_thrones.model.Welcome;
import com.seay.game_of_thrones.network.service.ApiService;
import com.seay.game_of_thrones.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private GameOfThronesApplication application;

    public ApplicationModule(GameOfThronesApplication application){
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return application;
    }

    @Provides
    @Singleton
    Welcome provideWelcome() {return new Welcome();}

    @Provides
    @Singleton
    ApiService provideApiService(Context context) {
        return new ApiService(context);
    }

}
