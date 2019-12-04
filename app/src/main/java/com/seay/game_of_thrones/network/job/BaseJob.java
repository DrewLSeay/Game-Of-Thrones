package com.seay.game_of_thrones.network.job;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.seay.game_of_thrones.inject.AppComponent;

public abstract class BaseJob extends Job {

    public static final int PRIORITY = 1;

    public static final String BASE_TAG = "BASE_TAG";

    public static final int INITIAL_BACKOFF = 1000;

    public BaseJob(Params params) {
        super(params.addTags(BASE_TAG));
    }

    public void inject(AppComponent appComponent) {

    }

}
