package com.seay.game_of_thrones.inject;

import com.seay.game_of_thrones.activity.MainActivity;
import com.seay.game_of_thrones.androidViewModel.CharacterAndroidViewModel;
import com.seay.game_of_thrones.model.Welcome;
import com.seay.game_of_thrones.network.job.GetCharacterListJob;
import com.seay.game_of_thrones.repository.CharacterRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface AppComponent {

    //Activities
    void inject(MainActivity activity);

    //AndroidViewModels
    void inject(CharacterAndroidViewModel viewModel);

    //Models
    void inject(Welcome welcome);

    //Jobs
    void inject(GetCharacterListJob job);

    //Repositories
    void inject(CharacterRepository repository);
}
