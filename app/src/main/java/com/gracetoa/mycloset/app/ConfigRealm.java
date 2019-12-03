package com.gracetoa.mycloset.app;

import android.app.Application;

import com.gracetoa.mycloset.models.Category;
import com.gracetoa.mycloset.models.SubCategory;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Project My Closet.
 * Created by gracetoa on 2019-11-16.
 */
public class ConfigRealm extends Application {

    public static AtomicInteger CategoryID = new AtomicInteger();
    public static AtomicInteger SubCategoryID = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();

//        Realm.init(this);
        setUpRealmConfig();

        Realm realm = Realm.getDefaultInstance();
        CategoryID = getIdByTable(realm, Category.class);
        SubCategoryID = getIdByTable(realm, SubCategory.class);
        realm.close();
    }


    private void setUpRealmConfig() {

        RealmConfiguration configuration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();

        return  (results.size()>0) ? new AtomicInteger(results.max("id").intValue()) : new AtomicInteger();

    }
}
