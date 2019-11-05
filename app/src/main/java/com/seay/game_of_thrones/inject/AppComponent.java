package com.seay.game_of_thrones.inject;

import com.seay.game_of_thrones.activity.MainActivity;
import com.seay.game_of_thrones.model.Welcome;
import com.seay.game_of_thrones.network.service.ApiServiceInterface;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface AppComponent {

    //Activities
    void inject(MainActivity activity);

    //Models
    void inject(Welcome welcome);

    //Networking
    void inject(ApiServiceInterface apiServiceInterface);
}
