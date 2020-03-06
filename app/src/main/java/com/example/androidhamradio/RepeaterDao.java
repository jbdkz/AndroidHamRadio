// Project:     Capstone Android Project
// Author:      John Diczhazy
// File:        RepeaterDao.java
// Description: DAO (Data Access Object), used to communicate with SQLite.
//                  Part of the Model in MVVM (Model-View-ViewModel)

package com.example.androidhamradio;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

// @Dao indicates it is a DAO interface
@Dao
public interface RepeaterDao
{

    // methods for all the operations we want to do on our Repeater table
    @Insert
    void insert(Repeater repeater);

    @Update
    void update(Repeater repeater);

    @Delete
    void delete(Repeater repeater);

    @Query("DELETE FROM repeater_table")
    void deleteAllRepeaters();

    // when data is retrieved, order alphabetically by state
    @Query("SELECT * FROM repeater_table ORDER BY state ASC")
    LiveData<List<Repeater>> getAllRepeaters();
}
