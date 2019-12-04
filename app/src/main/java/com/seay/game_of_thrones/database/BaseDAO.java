package com.seay.game_of_thrones.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.List;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObject;
import timber.log.Timber;

public abstract class BaseDAO {
    /**
     * This can be used to listen for when Realm is done writing.
     */
    public interface RealmListener {
        void transactionCompleted();

        void errorOccurred(Throwable error);
    }

    /**
     * This is a Custom Transaction Listener that will automatically Log stuff and will also update the RealmListener if not null.
     */
    private class MyTransactionListener<E extends RealmModel> extends Realm.Transaction.Callback implements Realm.Transaction.OnSuccess, Realm.Transaction.OnError {

        Realm realm;
        RealmListener realmListener;
        E realmObject;
        Iterable<E> realmObjectList;
        String transactionTitle;

        /**
         * Creates a Custom Transaction Listener. Uses the transactionTitle to log the results.
         *
         * @param realm            The Realm we are using.
         * @param realmListener    The Listener that will get updates on Realm Read / Writes.
         * @param transactionTitle The title that describes the Realm Transaction. Only used in Logging. Not used if a realm object is present.
         */
        MyTransactionListener(@NonNull Realm realm, @Nullable RealmListener realmListener, @Nullable String transactionTitle) {
            this.realm = realm;
            this.realmListener = realmListener;
            this.transactionTitle = transactionTitle;
        }

        /**
         * Creates a Custom Transaction Listener.
         *
         * @param realm            The Realm we are using.
         * @param realmObject      The Object you are writing to Realm.
         * @param realmListener    The Listener that will get updates on Realm Read / Writes.
         * @param transactionTitle The title that describes the Realm Transaction. Only used in Logging. Not used if a realm object is present.
         */
        MyTransactionListener(@NonNull Realm realm, @Nullable E realmObject, @Nullable RealmListener realmListener, @Nullable String transactionTitle) {
            this.realm = realm;
            this.realmObject = realmObject;
            this.realmListener = realmListener;
            this.transactionTitle = transactionTitle;
        }

        /**
         * Creates a Custom Transaction Listener.
         *
         * @param realm         The Realm we are using.
         * @param realmObject   The Object you are writing to Realm.
         * @param realmListener The Listener that will get updates on Realm Read / Writes.
         */
        MyTransactionListener(@NonNull Realm realm, @Nullable E realmObject, @Nullable RealmListener realmListener) {
            this(realm, realmObject, realmListener, null);
        }

        /**
         * Creates a Custom Transaction Listener.
         *
         * @param realm            The Realm we are using.
         * @param realmObject      The Object you are writing to Realm.
         * @param realmListener    The Listener that will get updates on Realm Read / Writes.
         * @param transactionTitle The title that describes the Realm Transaction. Only used in Logging. Not used if a realm object is present.
         */
        MyTransactionListener(@NonNull Realm realm, @Nullable Iterable<E> realmObject, @Nullable RealmListener realmListener, @Nullable String transactionTitle) {
            this.realm = realm;
            this.realmObjectList = realmObject;
            this.realmListener = realmListener;
            this.transactionTitle = transactionTitle;
        }

        /**
         * Creates a Custom Transaction Listener.
         *
         * @param realm         The Realm we are using.
         * @param realmObject   The Object you are writing to Realm.
         * @param realmListener The Listener that will get updates on Realm Read / Writes.
         */
        MyTransactionListener(@NonNull Realm realm, @Nullable Iterable<E> realmObject, @Nullable RealmListener realmListener) {
            this(realm, realmObject, realmListener, null);
        }

        @Override
        public void onSuccess() {
            super.onSuccess();
            if (realmObject != null) {
                Timber.i("%s: Successfully written to Realm.", realmObject.getClass().getSimpleName());
            } else if (realmObjectList != null) {
                Timber.i("%s: Successfully written to Realm.", realmObjectList.getClass().getSimpleName());
            } else if (transactionTitle != null) {
                Timber.i("Successful Realm Transaction: %s", transactionTitle);
            } else {
                Timber.i("Successful Realm Transaction");
            }

            if (realmListener != null) {
                realmListener.transactionCompleted();
            }

            realm.close();
        }

        @Override
        public void onError(Throwable error) {
            if (realmObject != null) {
                Timber.e(realmObject.getClass().getSimpleName() + ": Error writing to Realm. Error message: " + error.getLocalizedMessage());
            } else if (realmObjectList != null) {
                Timber.e(realmObjectList.getClass().getSimpleName() + ": Error writing to Realm. Error message: " + error.getLocalizedMessage());
            } else if (transactionTitle != null) {
                Timber.e("Failed Realm Transaction: %s", transactionTitle);
            } else {
                Timber.e("Failed Realm Transaction");
            }

            if (realmListener != null) {
                realmListener.errorOccurred(error);
            }

            realm.close();
        }
    }

    // --------------------------------------
    // ----------- Realm Functions ----------
    // --------------------------------------

    /**
     * Delete Realm.
     */
    protected void deleteRealm(@NonNull RealmConfiguration realmConfiguration) {
        DynamicRealm dynamicAccessTokenRealm = DynamicRealm.getInstance(realmConfiguration);
        dynamicAccessTokenRealm.executeTransaction(realm1 -> realm1.deleteAll());
        dynamicAccessTokenRealm.close();
    }

    /**
     * Will write an Object to Realm, if the Object already exists, it will be updated.
     *
     * @param realmObject   The object we want to save.
     * @param realmListener The Listener that will get hit once the write is completed.
     */
    <E extends RealmModel> void writeOrUpdateRealmObject(@NonNull E realmObject, @Nullable RealmListener realmListener) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(realmAsync -> realmAsync.insertOrUpdate(realmObject), new MyTransactionListener<>(realm, realmObject, realmListener),
                new MyTransactionListener<>(realm, realmObject, realmListener));
    }

    /**
     * Will write an Object to Realm, if the Object already exists, it will be updated.
     *
     * @param realmObject The object we want to save.
     */
    <E extends RealmModel> void writeOrUpdateRealmObjectSynchronouslyWithCustomConfig(@NonNull RealmConfiguration realmConfiguration, @NonNull E realmObject) {
        Realm realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmSync -> realmSync.insertOrUpdate(realmObject));
        realm.close();
    }

    /**
     * Will write an Object to Realm, if the Object already exists, it will be updated.
     *
     * @param realmObject   The object we want to save.
     * @param realmListener The Listener that will get hit once the write is completed.
     */
    <E extends RealmModel> void writeOrUpdateRealmObjectWithCustomConfig(@NonNull RealmConfiguration realmConfiguration, @NonNull E realmObject, @Nullable RealmListener realmListener) {
        Realm realm = Realm.getInstance(realmConfiguration);

        realm.executeTransactionAsync(realmAsync -> realmAsync.insertOrUpdate(realmObject), new MyTransactionListener<>(realm, realmObject, realmListener),
                new MyTransactionListener<>(realm, realmObject, realmListener));
    }

    /**
     * Will write a List of objects to Realm, if the Objects already exist, they will be updated.
     *
     * @param realmObject   The object we want to save.
     * @param realmListener The Listener that will get hit once the write is completed.
     */
    <E extends RealmModel> void writeOrUpdateRealmObject(@NonNull Collection<E> realmObject, @Nullable RealmListener realmListener) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(realmAsync -> realmAsync.insertOrUpdate(realmObject), new MyTransactionListener<>(realm, realmObject, realmListener),
                new MyTransactionListener<>(realm, realmObject, realmListener));
    }

    /**
     * Will write an Object to Realm, if the Object already exists, it will be updated.
     *
     * @param realmObject The object we want to save.
     */
    <E extends RealmModel> void writeOrUpdateRealmObjectSynchronously(@NonNull E realmObject) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realmSync -> realmSync.insertOrUpdate(realmObject));
        realm.close();
    }

    /**
     * Will write an Object to Realm, if the Object already exists, it will be updated.
     *
     * @param realmObject The object we want to save.
     */
    <E extends RealmModel> void writeOrUpdateRealmObjectWithCustomConfigSynchronously(@NonNull RealmConfiguration realmConfiguration, @NonNull E realmObject) {
        Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmSync -> realmSync.insertOrUpdate(realmObject));
        realm.close();
    }

    /**
     * Will write a List of objects to Realm, if the Objects already exist, they will be updated.
     *
     * @param realmObject The object we want to save.
     */
    <E extends RealmModel> void writeOrUpdateRealmObjectSynchronously(@NonNull Collection<E> realmObject) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realmSync -> realmSync.insertOrUpdate(realmObject));
        realm.close();
    }

    /**
     * Gets a Copy of an Object from Realm. Or NULL if the original is null.
     *
     * @param realm    The realm we are using. This will be closed once the copy is done.
     * @param original The original managed Realm Object.
     * @param <E>      Must extend Realm Object.
     * @return An un-managed copy of the Realm Object.
     */
    static <E extends RealmObject> E getRealmCopyThenCloseRealm(@NonNull Realm realm, @Nullable E original) {

        E copy = null;
        if (original != null) {
            copy = realm.copyFromRealm(original);
        }

        // Close the realm that was passed in. Make sure to not close it in the calling function.
        realm.close();

        return copy;
    }

    /**
     * Gets a Copy of an Object from Realm. Or NULL if the original is null.
     *
     * @param realm    The realm we are using. This will be closed once the copy is done.
     * @param original The original managed Realm Object.
     * @param <E>      Must extend Realm Object.
     * @return An un-managed copy of the Realm Object.
     */
    static <E extends RealmModel> List<E> getRealmCopyThenCloseRealm(@NonNull Realm realm, @Nullable List<E> original) {

        List<E> copy = null;
        if (original != null) {
            copy = realm.copyFromRealm(original);
        }

        // Close the realm that was passed in. Make sure to not close it in the calling function.
        realm.close();

        return copy;
    }

}
