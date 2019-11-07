package com.seay.game_of_thrones.model;

import android.content.Context;

import com.seay.game_of_thrones.R;
import com.seay.game_of_thrones.inject.Injector;

import javax.inject.Inject;

public class Welcome {

    @Inject
    Context context;

    public Welcome() {
        Injector.obtain().inject(this);
    }

    public String getWelcomeString() {
        return context.getString(R.string.app_name);
    }


}
