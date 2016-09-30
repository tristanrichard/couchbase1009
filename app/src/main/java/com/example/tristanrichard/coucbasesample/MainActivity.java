package com.example.tristanrichard.coucbasesample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private ActivityTask activityTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityTask = new ActivityTask(this);
        activityTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private static Database getDatabase(String cache) throws CouchbaseLiteException {
        switch (cache) {
            case SampleAppication.USER_DB:
                return SampleAppication.getInstance().getUserDatabaseInstance();
            default:
                return SampleAppication.getInstance().getMetaDatabaseInstance();
        }

    }

    public void store(String cache, String id, String content) {

        Document retrievedDocument = null;
        try {
            retrievedDocument = getDatabase(cache).getDocument(id);
            Map<String, Object> updatedProperties = new HashMap<String, Object>();
            Map<String, Object> currentProperties = retrievedDocument.getProperties();
            if (currentProperties != null && currentProperties.size() != 0) {
                updatedProperties.putAll(currentProperties);
            }
            updatedProperties.put("1", content);
            retrievedDocument.putProperties(updatedProperties);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public String retreive(String cache, String id) {

        try {
            Document retrievedDocument = getDatabase(cache).getDocument(id);
            Object object = retrievedDocument.getProperty("1");
            if (object == null) return "";
            return object.toString();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            return "";
        }
    }

    static class ActivityTask extends AsyncTask<String, Void, Void> {

        private MainActivity delegate;

        public ActivityTask(MainActivity delegate) {
            this.delegate = delegate;
        }

        protected Void doInBackground(String... urls) {

            // Collect all accounts in data
            ArrayList<Account> accountsToStore = new ArrayList<>();
            for (int i = 0; i < 100 ;i++) {
                accountsToStore.add(new Account("firstname"+i, "lastname", "dev",i,"DK"));
            }
            // Load collected accounts into cache
            Gson gson = new Gson();
            for (Account account : accountsToStore) {
                delegate.store(SampleAppication.USER_DB, "Account"+account.getAge(), gson.toJson(account));
                if (account.getAge() == 50) {
                    publishProgress();
                }
            }

            for (int i = 0; i < 100 ;i++) {
                Log.i("In db", delegate.retreive(SampleAppication.USER_DB, "Account"+i));
            }

            return null;
        }

        protected void onPostExecute(Void result) {

        }

        protected void onCancelled() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            SampleAppication.getInstance().resetUserDatabase();
        }
    }
}
