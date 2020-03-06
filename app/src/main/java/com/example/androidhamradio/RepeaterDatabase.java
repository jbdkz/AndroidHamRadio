// Project:     Capstone Android Project
// Author:      John Diczhazy
// File:        RepeaterDatabase.java
// Description: Abstract class RepeaterDatabase extends RoomDatabase. Room persistence library
//               provides an abstraction layer over SQLite

package com.example.androidhamradio;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// @Database is a Room annotation, define entities that we want this database to contain
@Database(entities = {Repeater.class}, version = 1)

//Abstract class RepeaterDatabase extends RoomDatabase
public abstract class RepeaterDatabase extends RoomDatabase
{

    // required to turn class into singleton, only one instance of database!
    private static RepeaterDatabase instance;

    // abstract method used to access repeaterDao
    public abstract com.example.androidhamradio.RepeaterDao repeaterDao();

    // required to turn class into singleton, returns Repeater database instance.
    //    synchronized indicates only one thread at a time can access this method
    public static synchronized RepeaterDatabase getInstance(Context context)
    {

        // only create instance of database if one does not exist
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RepeaterDatabase.class, "repeater_database")

                    // .addCallBack will create test data on database creation
                    //   commented out as this was for testing purposes only
                    //.addCallback(roomCallback)//***CREATE DATA***
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    // Populate repeater database with test data during creation
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // call PopulateDbAsyncTask
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // execute populate operation in background thread to make gui responsive
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private RepeaterDao repeaterDao;

        private PopulateDbAsyncTask(RepeaterDatabase db) {
            repeaterDao = db.repeaterDao();
        }

        // insert test repeater data into database
        @Override
        protected Void doInBackground(Void... voids) {
            repeaterDao.insert(new Repeater("call1", "county1", "downlinktone1",
                    "inputFreq1", "location1", "offset1", "outputfreq1",
                    "state1", "uplinktone1", "use1"));
            repeaterDao.insert(new Repeater("call2", "county2", "downlinktone2",
                    "inputFreq2", "location2", "offset2", "outputfreq2",
                    "state2", "uplinktone2", "use2"));
            repeaterDao.insert(new Repeater("call3", "county3", "downlinktone3",
                    "inputFreq3", "location3", "offset3", "outputfreq3",
                    "state3", "uplinktone3", "use3"));



            return null;
        }
    }
}