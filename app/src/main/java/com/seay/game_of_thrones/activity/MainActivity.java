package com.seay.game_of_thrones.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.TextView;

import com.seay.game_of_thrones.R;
import com.seay.game_of_thrones.androidViewModel.CharacterAndroidViewModel;
import com.seay.game_of_thrones.database.realmObjects.CharacterInformation;
import com.seay.game_of_thrones.inject.Injector;
import com.seay.game_of_thrones.model.Welcome;
import com.seay.game_of_thrones.network.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private CharacterAndroidViewModel characterAndroidViewModel;

    @Inject
    Welcome welcome;

    @Inject
    ApiService apiService;

    @BindView(R.id.helloText)
    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Injector.obtain().inject(this);

        characterAndroidViewModel = ViewModelProviders.of(this).get(CharacterAndroidViewModel.class);

        characterAndroidViewModel.getCharactersInformation().observe(this, MainActivity.this::setResults);

        welcomeTextView.setText(welcome.getWelcomeString());
    }

    private void setResults(@Nullable List<CharacterInformation> results) {
        if (results == null) {
            results = new ArrayList<>();
        }

        if (!results.isEmpty()) {
            welcomeTextView.setText(results.get(0).getDescription());
        }
    }
}

