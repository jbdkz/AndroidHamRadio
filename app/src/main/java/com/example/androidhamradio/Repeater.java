// Project:     Capstone Android Project
// Author:      John Diczhazy
// File:        Repeater.java
// Description: Room persistence library, wrapper around SQLite. Part of the
//                Model in MVVM (Model-View-ViewModel)

package com.example.androidhamradio;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Room annotation at compile time, it will create required code to create SQLite table
//  for this object.
@Entity(tableName = "repeater_table")

public class Repeater
{

    // create member variables for all the fields we want the table to contain
    // id is the primary key in the SQLite table, unique id will be automatically generated
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String call;
    private String county;
    private String downlinkTone;
    private String inputFreq;
    private String location;
    private String offset;
    private String outputFreq;
    private String state;
    private String uplinkTone;
    private String use;

    // constructor required to create Repeater objects
    public Repeater(String call, String county, String downlinkTone, String inputFreq, String location, String offset,
                    String outputFreq, String state, String uplinkTone, String use)
    {
        this.call = call;
        this.county = county;
        this.downlinkTone = downlinkTone;
        this.inputFreq = inputFreq;
        this.location = location;
        this.offset  = offset;
        this.outputFreq = outputFreq;
        this.state = state;
        this.uplinkTone = uplinkTone;
        this.use = use;
    }

    // setter method required to create set id
    public void setId(int id) {
        this.id = id;
    }

    // public getter methods for SQLite fields
    public int getId() {
        return id;
    }
    public String getCall() {
        return call;
    }
    public String getCounty() {
        return county;
    }
    public String getDownlinkTone() {
        return downlinkTone;
    }
    public String getInputFreq() {
        return inputFreq;
    }
    public String getLocation() {
        return location;
    }
    public String getOffset() {
        return offset;
    }
    public String getOutputFreq() {
        return outputFreq;
    }
    public String getState() {
        return state;
    }
    public String getUplinkTone() {
        return uplinkTone;
    }
    public String getUse() {
        return use;
    }
}
