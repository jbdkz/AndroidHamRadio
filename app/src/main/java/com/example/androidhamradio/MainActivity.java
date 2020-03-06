// Project:     Capstone Android Project
// Author:      John Diczhazy
// File:        MainActivity.java
// Description: Project implements MVVM Model-View-ViewModel for access to SQLite database.

// NOTES:
// *** Remember, changes were made to build.gradle (Module: app) ***
// *** Remember, changes were made to the AndroidManifest.xml file ***
// In AndroidManifest.xml, android:launchmode = "singleTop" was removed in
//  activity android:name=".MainActivity" as it caused the font color change in the
//  main view to not work correctly
//
// RepeaterDatabase class creates database
//
// Object Associations
//
// Repeater <=> RepeaterDao <=> RepeaterRepository <=> RepeaterViewModel <=> MainActivity
//
// AddEditRepeaterActivity object uses intent to pass data to MainActivity object
//
// MainActivity object passes data to RepeaterAdapter object

// REFERENCES:
//
// RecyclerView Tutorial:
// https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-1-introduction
//
// Confirm Dialog Example:
// https://stackoverflow.com/questions/50137310/confirm-dialog-before-swipe-delete-using-itemtouchhelper
//
// Icon Generator:
// https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html#foreground.type=image&foreground.space.trim=1&foreground.space.pad=0.25&foreColor=rgba(96%2C%20125%2C%20139%2C%200)&backColor=rgb(68%2C%20138%2C%20255)&crop=0&backgroundShape=circle&effects=none&name=ic_launcher
// replace icons when app is not running both square and round

package com.example.androidhamradio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DeleteAllDialogFragment.ResetDialogListener
{
    // Constants used to differentiate Add or Edit requests
    public static final int ADD_REPEATER_REQUEST = 1;
    public static final int EDIT_REPEATER_REQUEST = 2;

    // static variables used for setting TextColor in RepeaterAdapter RecyclerView
    public static int CALLSIGNFONTCOLORPUBLICINT = 0;
    public static int PARAMFONTCOLORPUBLICINT = 0;

    // create member variable
    private RepeaterViewModel repeaterViewModel;

    // references needed for preferences
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FloatingActionButton opens Activity used to Add/Edit Data
        FloatingActionButton buttonAddRepeater = findViewById(R.id.button_add_repeater);
        buttonAddRepeater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent is used to pass data back from AddEditRepeaterActivity back to MainActivity
                Intent intent = new Intent(MainActivity.this, AddEditRepeaterActivity.class);
                startActivityForResult(intent, ADD_REPEATER_REQUEST);
            }
        });

        // setup reference to RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // required RecyclerView layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // associate RepeaterAdapter with RecyclerView
        final RepeaterAdapter adapter = new RepeaterAdapter();
        recyclerView.setAdapter(adapter);

        // get instance of repeaterViewModel
        repeaterViewModel = ViewModelProviders.of(this).get(RepeaterViewModel.class);

        // insert and delete example
        //repeaterViewModel.insert(new Repeater("call4", "county4",
        //        "downlinktone4", "inputFreq4", "location4", "offset4",
        //        "outputfreq4","state4", "uplinktone4", "use4"));
        //repeaterViewModel.delete(adapter.getRepeaterAt(1));

        // take repeaterViewModel, return all repeaters from database
        repeaterViewModel.getAllRepeaters().observe(this, new Observer<List<Repeater>>() {




            @Override
            // updates data in adapter whenever onChange is triggered
            public void onChanged(@Nullable List<Repeater> repeaters) {
                adapter.submitList(repeaters);
            }
        });


        // this class makes RecyclerView swipeable
        // ItemTouchHelper values
        //   dragDirs: 0 drag and drop not used
        //   swipeDirs: ItemTouchHelper.LEFT allow left swipe
        //   swipeDirs: ItemTouchHelper.RIGHT allow right swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
            {
            // method used for drag and drop capability, not used in this project
             @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            // method called during swipe
           @Override
         public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction)
           {

                // Original Delete Code
                //repeaterViewModel.delete(adapter.getRepeaterAt(viewHolder.getAdapterPosition()));
                //Toast.makeText(MainActivity.this, "Repeater deleted", Toast.LENGTH_SHORT).show();

                // AlertDialog used for delete confirmation
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle( "Delete Confirmation" )
                        .setIcon(R.drawable.ic_add)
                        .setMessage("Delete Repeater?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                // refresh RecyclerView contents
                                adapter.notifyDataSetChanged();

                                // indicate no changes made
                                Toast.makeText(MainActivity.this, "No action taken", Toast.LENGTH_SHORT).show();
                                dialoginterface.dismiss();
                          }})
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                // delete repeater at position
                                repeaterViewModel.delete(adapter.getRepeaterAt(viewHolder.getAdapterPosition()));

                                // toast indicates delete successful
                                Toast.makeText(MainActivity.this, "Repeater deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                }
                    // must pass recyclerView object
                             }).attachToRecyclerView(recyclerView);


        // get data to be passed to AddEditRepeater View
        adapter.setOnItemClickListener(new RepeaterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Repeater repeater) {
                // create new Intent for AddEditRepeater View
                Intent intent = new Intent(MainActivity.this, AddEditRepeaterActivity.class);
                // get data to be passed to AddEditRepeater View
                intent.putExtra(AddEditRepeaterActivity.EXTRA_ID, repeater.getId());
                intent.putExtra(AddEditRepeaterActivity.EXTRA_CALL, repeater.getCall());
                intent.putExtra(AddEditRepeaterActivity.EXTRA_COUNTY, repeater.getCounty());
                intent.putExtra(AddEditRepeaterActivity.EXTRA_DOWNLINKTONE, repeater.getDownlinkTone());
                intent.putExtra(AddEditRepeaterActivity.EXTRA_INPUTFREQ, repeater.getInputFreq());
                intent.putExtra(AddEditRepeaterActivity.EXTRA_LOCATION, repeater.getLocation());
                intent.putExtra(AddEditRepeaterActivity.EXTRA_OFFSET, repeater.getOffset());
                intent.putExtra(AddEditRepeaterActivity.EXTRA_OUTPUTFREQ, repeater.getOutputFreq());
                intent.putExtra(AddEditRepeaterActivity.EXTRA_STATE, repeater.getState());
                intent.putExtra(AddEditRepeaterActivity.EXTRA_UPLINKTONE, repeater.getUplinkTone());
                intent.putExtra(AddEditRepeaterActivity.EXTRA_USE, repeater.getUse());

                // start activity for AddEditRepeater View passing the required data
                startActivityForResult(intent, EDIT_REPEATER_REQUEST);
            }
        });

        // get the data from the preferences with default if no preference
        // set up preferences in private mode
        preferenceSettings = getSharedPreferences("PREFS", PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        // set the default Call Sign Font Color
        CALLSIGNFONTCOLORPUBLICINT = preferenceSettings.getInt("CALLSIGNFONTCOLORINT", 0);

        // set the default Parameters Font Color
        PARAMFONTCOLORPUBLICINT = preferenceSettings.getInt("PARAMFONTCOLORINT", 0);
    }

    // get data back from Intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // test if add operation and if successful
        if (requestCode == ADD_REPEATER_REQUEST && resultCode == RESULT_OK)
        {
            // put Intent data into local variables
            String call = data.getStringExtra(AddEditRepeaterActivity.EXTRA_CALL);
            String county = data.getStringExtra(AddEditRepeaterActivity.EXTRA_COUNTY);
            String downlinkTone = data.getStringExtra(AddEditRepeaterActivity.EXTRA_DOWNLINKTONE);
            String inputFreq = data.getStringExtra(AddEditRepeaterActivity.EXTRA_INPUTFREQ);
            String location = data.getStringExtra(AddEditRepeaterActivity.EXTRA_LOCATION);
            String offset = data.getStringExtra(AddEditRepeaterActivity.EXTRA_OFFSET);
            String outputFreq = data.getStringExtra(AddEditRepeaterActivity.EXTRA_OUTPUTFREQ);
            String state = data.getStringExtra(AddEditRepeaterActivity.EXTRA_STATE);
            String uplinkTone = data.getStringExtra(AddEditRepeaterActivity.EXTRA_UPLINKTONE);
            String use = data.getStringExtra(AddEditRepeaterActivity.EXTRA_USE);

            // create new Repeater, pass required data
            Repeater repeater = new Repeater(call, county, downlinkTone, inputFreq, location, offset, outputFreq, state, uplinkTone, use);

            // insert repeater into database
            repeaterViewModel.insert(repeater);

            // display toast message that repeater has been saved
            Toast.makeText(this, "Repeater saved", Toast.LENGTH_SHORT).show();
        }

        // test if edit operation and if successful
        else if (requestCode == EDIT_REPEATER_REQUEST && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(AddEditRepeaterActivity.EXTRA_ID, -1);

            // check for valid id before update
            if (id == -1) {
                // Toast to indicate update failed
                Toast.makeText(this, "Repeater can't be updated", Toast.LENGTH_SHORT).show();

                // call return to leave this method
                return;
            }

            // put Intent data into local variables
            String call = data.getStringExtra(AddEditRepeaterActivity.EXTRA_CALL);
            String county = data.getStringExtra(AddEditRepeaterActivity.EXTRA_COUNTY);
            String downlinkTone = data.getStringExtra(AddEditRepeaterActivity.EXTRA_DOWNLINKTONE);
            String inputFreq = data.getStringExtra(AddEditRepeaterActivity.EXTRA_INPUTFREQ);
            String location = data.getStringExtra(AddEditRepeaterActivity.EXTRA_LOCATION);
            String offset = data.getStringExtra(AddEditRepeaterActivity.EXTRA_OFFSET);
            String outputFreq = data.getStringExtra(AddEditRepeaterActivity.EXTRA_OUTPUTFREQ);
            String state = data.getStringExtra(AddEditRepeaterActivity.EXTRA_STATE);
            String uplinkTone = data.getStringExtra(AddEditRepeaterActivity.EXTRA_UPLINKTONE);
            String use = data.getStringExtra(AddEditRepeaterActivity.EXTRA_USE);

            // create new repeater object with required data
            Repeater repeater = new Repeater(call, county, downlinkTone, inputFreq, location, offset, outputFreq, state, uplinkTone, use);
            // update will not happen without required id
            repeater.setId(id);
            // update repeater, pass required data
            repeaterViewModel.update(repeater);

            // display toast message that repeater has been updated
            Toast.makeText(this, "Repeater updated", Toast.LENGTH_SHORT).show();
            }
        else
            // indicate repeater has not been saved it add or update operation fails
            {
            Toast.makeText(this, "Repeater not saved", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    // display top menu in main view
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        // must return true so that menu will be displayed
        return true;
    }

    @Override
    // display dialog when menu item selected
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.delete_all_repeaters:
                // create the dialog
                DialogFragment newFragment = new DeleteAllDialogFragment();
                // show it
                newFragment.show(getSupportFragmentManager(), "deleteall");
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDeleteAllDialogPositiveClick()
    {
        // delete all Repeaters in database
        repeaterViewModel.deleteAllRepeaters();
        // Toast to show user all Repeaters were deleted
        Toast.makeText(this, "All repeaters deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteAllDialogNegativeClick()
    {

    }
}