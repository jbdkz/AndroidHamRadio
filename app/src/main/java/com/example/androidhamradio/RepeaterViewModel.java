// Project:     Capstone Android Project
// Author:      John Diczhazy
// File:        RepeaterViewModel.java
// Description: Holds and prepares data for the user interface. Part of the
//               ViewModel in MVVM (Model-View-ViewModel) Interacts with RepeaterRepository

package com.example.androidhamradio;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

// LiveData is a data holder class that can be observed within the application lifecycle.
import androidx.lifecycle.LiveData;

import java.util.List;

public class
RepeaterViewModel extends AndroidViewModel
{

    // create member variables
    private RepeaterRepository repository;
    private LiveData<List<Repeater>> allRepeaters;

    // create constructor method
    public RepeaterViewModel(@NonNull Application application)
    {
        super(application);

        // instantiate two member variables in constructor
        repository = new RepeaterRepository(application);
        allRepeaters = repository.getAllRepeaters();
    }

    // create repository methods for database operations
    public void insert(Repeater repeater) {
        repository.insert(repeater);
    }

    public void update(Repeater repeater) {
        repository.update(repeater);
    }

    public void delete(Repeater repeater) {repository.delete(repeater);}

    public void deleteAllRepeaters() {
        repository.deleteAllRepeaters();
    }

    public LiveData<List<Repeater>> getAllRepeaters() {
        return allRepeaters;
    }
}