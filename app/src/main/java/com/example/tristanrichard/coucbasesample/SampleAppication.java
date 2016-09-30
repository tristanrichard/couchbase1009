package com.example.tristanrichard.coucbasesample;

import android.app.Application;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.File;
import java.io.IOException;

/**
 * Created by tristanrichard on 30/09/2016.
 */

public class SampleAppication extends Application {

    private static SampleAppication instance;
    private Manager manager = null;
    private Database user_database = null;
    private Database meta_database = null;
    public static final String USER_DB = "user";
    public static final String META_DB = "meta";

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static SampleAppication getInstance(){
        if (instance == null){
            instance = new SampleAppication();
        }
        return instance;
    }

    public Database getUserDatabaseInstance() throws CouchbaseLiteException {
        if (this.user_database == null) {
            try {
                this.user_database = getManagerInstance().getDatabase(USER_DB);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (this.user_database == null && this.manager != null){
                File srcDir = new File(manager.getContext().getFilesDir(), USER_DB +".cblite2");
                this.manager.replaceDatabase(USER_DB, srcDir.getAbsolutePath());
            }
        }
        return user_database;
    }
    public void resetUserDatabase(){
        if (this.user_database != null){
            try {
                this.user_database.delete();
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }
        this.user_database = null;
    }

    public Database getMetaDatabaseInstance() throws CouchbaseLiteException {
        if (this.meta_database == null) {
            try {
                this.meta_database = getManagerInstance().getDatabase(META_DB);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (this.meta_database == null && this.manager != null){
                File srcDir = new File(manager.getContext().getFilesDir(), META_DB +".cblite2");
                this.manager.replaceDatabase(META_DB, srcDir.getAbsolutePath());
            }
        }
        return meta_database;
    }

    public void resetMetaDatabase(){
        if (this.meta_database != null){
            try {
                this.meta_database.delete();
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }
        this.meta_database = null;
    }

    public Manager getManagerInstance() throws IOException {
        if (manager == null) {
            manager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);
        }
        return manager;
    }

}
