package com.seay.game_of_thrones.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.seay.game_of_thrones.database.realmObjects.CharacterInformation;
import com.seay.game_of_thrones.database.realmObjects.LiveRealmData;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;


public class MainDAO extends BaseDAO {
    private static final String REALM_NAME = "seay.GameofThrones.realm";

    private static final int REALM_SCHEMA_VERSION = 0;

    //region Init

    /**
     * Initializes Realm.
     *
     * @param context The Application Context.
     */
    public static void init(@NonNull Context context) {
        Realm.init(context);
        Realm.setDefaultConfiguration(getRealmConfiguration());
    }

    /**
     * Gets a RealmConfiguration. This is where we set the Realm name and version.
     *
     * @return The RealmConfiguration.
     */
    private static RealmConfiguration getRealmConfiguration() {
        RealmConfiguration.Builder realmBuilder = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(REALM_SCHEMA_VERSION)
                .deleteRealmIfMigrationNeeded();

        return realmBuilder.build();
    }
    //endregion

    //region Character List
    public LiveRealmData<CharacterInformation> findAllCharacterInformation(@NonNull Realm realm) {
        return new LiveRealmData<>(realm.where(CharacterInformation.class).findAllAsync());
    }
    //endregion

    //region Save Functions
    public <T extends RealmObject> void save(@NonNull T realmObject) {
        writeOrUpdateRealmObjectSynchronously(realmObject);
    }

    public <T extends RealmObject> void save(@NonNull List<T> realmObjects) {
        writeOrUpdateRealmObjectSynchronously(realmObjects);
    }

    public <T extends RealmObject> void save(@NonNull T realmObject, @Nullable RealmListener listener) {
        writeOrUpdateRealmObject(realmObject, listener);
    }

    public <T extends RealmObject> void save(@NonNull List<T> realmObjects, @Nullable RealmListener listener) {
        writeOrUpdateRealmObject(realmObjects, listener);
    }
    //endregion

    public void deleteRealm() {
        deleteRealm(getRealmConfiguration());
    }

}
