package com.seay.game_of_thrones.database.realmObjects;

import androidx.lifecycle.MutableLiveData;

import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * This is a wrapper for the RealmResults to expose them as Lifecycle aware LiveData.
 */
public class LiveRealmData<T extends RealmModel> extends MutableLiveData<RealmResults<T>> {

    public static final String TAG = LiveRealmData.class.getSimpleName();

    private RealmResults<T> results;
    private final RealmChangeListener<RealmResults<T>> listener = this::setValue;

    public LiveRealmData(RealmResults<T> realmResults) {
        results = realmResults;
    }

    @Override
    protected void onActive() {
        Timber.v(TAG, "onActive: Adding Change Listener.");
        results.addChangeListener(listener);
        setValue(results); // Force a change call to be made...
        // May want to add some logic to only update UI if a change really did occur,
        // this is used to fix an issue where data was loaded and the change listener was not active
    }

    @Override
    protected void onInactive() {
        Timber.v(TAG, "onInactive: Removing Change Listener.");
        results.removeChangeListener(listener);
    }
}

