package com.seay.game_of_thrones.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.seay.game_of_thrones.R;
import com.seay.game_of_thrones.inject.Injector;
import com.seay.game_of_thrones.model.CharacterDTO;
import com.seay.game_of_thrones.model.Welcome;
import com.seay.game_of_thrones.network.service.ApiService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

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

        welcomeTextView.setText(welcome.getWelcomeString());


        apiService.getGoTCharacters(new Callback<List<CharacterDTO>>() {
            @Override
            public void onResponse(Call<List<CharacterDTO>> call, @NonNull Response<List<CharacterDTO>> response) {
                List<CharacterDTO> characters = response.body();

                if (characters != null) {
                    welcomeTextView.setText(characters.get(0).character);
                }
            }

            @Override
            public void onFailure(Call<List<CharacterDTO>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

