package com.seay.game_of_thrones.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.seay.game_of_thrones.R;
import com.seay.game_of_thrones.inject.Injector;
import com.seay.game_of_thrones.model.Welcome;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Inject
    Welcome welcome;

    @BindView(R.id.helloText)
    TextView welcomeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Injector.obtain().inject(this);

        welcomeTextView.setText(welcome.getWelcomeString());


    }
}

