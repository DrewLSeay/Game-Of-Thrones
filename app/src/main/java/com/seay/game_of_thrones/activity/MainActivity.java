package com.seay.game_of_thrones.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.seay.game_of_thrones.R;
import com.seay.game_of_thrones.inject.Injector;
import com.seay.game_of_thrones.model.GoTCharacter;
import com.seay.game_of_thrones.model.Welcome;
import com.seay.game_of_thrones.network.service.ApiService;
import com.seay.game_of_thrones.network.service.ApiServiceInterface;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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


        apiService.configApiService(this);

        apiService.getGoTCharacters(new Callback<List<GoTCharacter>>() {
            @Override
            public void onResponse(Call<List<GoTCharacter>> call, @NonNull Response<List<GoTCharacter>> response) {
                List<GoTCharacter> characters = response.body();

                if(characters != null){
                    welcomeTextView.setText(characters.get(0).getCharacterInformation());
                }
            }

            @Override
            public void onFailure(Call<List<GoTCharacter>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

