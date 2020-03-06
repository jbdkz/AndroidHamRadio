// Project:     Capstone Android Project
// Author:      John Diczhazy
// File:        RepeaterRepository.java
// Description: Simple java class that acts as an abstraction layer between the
//               RepeaterViewModel and RepeaterDAO. Allows connectivity of different data sources.
//               Part of the Model in MVVM (Model-View-ViewModel)

package com.example.androidhamradio;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;


public class RepeaterRepository
{
    // create member variables
    private RepeaterDao repeaterDao;
    private LiveData<List<Repeater>> allRepeaters;

    // create constructor to assign member variables
    public RepeaterRepository(Application application)
    {
        // get instance of repeater database
        RepeaterDatabase database = RepeaterDatabase.getInstance(application);
        // assign Data Access Object
        repeaterDao = database.repeaterDao();

        // assign getAllRepeaters method to variable
        allRepeaters = repeaterDao.getAllRepeaters();
    }

    // create method for database insert operation
    public void insert(Repeater repeater)
    {
        new InsertRepeaterAsyncTask(repeaterDao).execute(repeater);
    }

    // create method for database update operation
    public void update(Repeater repeater)
    {
        new UpdateRepeaterAsyncTask(repeaterDao).execute(repeater);
    }

    // create method for database delete operation
    public void delete(Repeater repeater)
    {
        new DeleteRepeaterAsyncTask(repeaterDao).execute(repeater);
    }

    // create method for database delete all operation
    public void deleteAllRepeaters() {
        new DeleteAllRepeatersAsyncTask(repeaterDao).execute();
    }

    // create method for database return all operation
    public LiveData<List<Repeater>> getAllRepeaters() {
        return allRepeaters;
    }

    // execute insert operation in background thread to make gui responsive
    private static class InsertRepeaterAsyncTask extends AsyncTask<Repeater, Void, Void> {
        private RepeaterDao repeaterDao;

        private InsertRepeaterAsyncTask(RepeaterDao repeaterDao) {
            this.repeaterDao = repeaterDao;
        }

        @Override
        protected Void doInBackground(Repeater... repeaters)
        {
            repeaterDao.insert(repeaters[0]);
            return null;
        }
    }

    // execute update operation in background thread to make gui responsive
    private static class UpdateRepeaterAsyncTask extends AsyncTask<Repeater, Void, Void>
    {
        private RepeaterDao repeaterDao;

        private UpdateRepeaterAsyncTask(RepeaterDao repeaterDao) {
            this.repeaterDao = repeaterDao;
        }

        @Override
        protected Void doInBackground(Repeater... repeaters)
        {
            repeaterDao.update(repeaters[0]);
            return null;
        }
    }

    // execute delete operation in background thread to make gui responsive
    private static class DeleteRepeaterAsyncTask extends AsyncTask<Repeater, Void, Void>
    {
        private RepeaterDao repeaterDao;

        private DeleteRepeaterAsyncTask(RepeaterDao repeaterDao) {
            this.repeaterDao = repeaterDao;
        }

        @Override
        protected Void doInBackground(Repeater... repeaters)
        {
            repeaterDao.delete(repeaters[0]);
            return null;
        }
    }

    // execute delete all operation in background thread to make gui responsive
    private static class DeleteAllRepeatersAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private RepeaterDao repeaterDao;

        private DeleteAllRepeatersAsyncTask(RepeaterDao repeaterDao)
        {
            this.repeaterDao = repeaterDao;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            repeaterDao.deleteAllRepeaters();
            return null;
        }
    }
}

