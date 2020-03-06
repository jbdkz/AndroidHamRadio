// Project:     Capstone Android Project
// Author:      John Diczhazy
// File:        AddEditRepeaterActivity.java
// Description: Assigned to activity_add_repeater layout, used to add or edit repeater object

package com.example.androidhamradio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

import java.util.Random;

public class AddEditRepeaterActivity extends AppCompatActivity
{

    // keys required to send data back to MainActivity using Intent, package name
    //  com.example.androidhamradio is used to keep them unique
    public static final String EXTRA_ID =
           "com.example.androidhamradio.EXTRA_ID";
    public static final String EXTRA_CALL =
            "com.example.androidhamradio.EXTRA_CALL";
    public static final String EXTRA_COUNTY =
            "com.example.androidhamradio.EXTRA_COUNTY";
    public static final String EXTRA_DOWNLINKTONE =
            "com.example.androidhamradio.EXTRA_DOWNLINKTONE";
    public static final String EXTRA_INPUTFREQ =
            "com.example.androidhamradio.EXTRA_INPUTFREQ";
    public static final String EXTRA_LOCATION =
            "com.example.androidhamradio.EXTRA_LOCATION";
    public static final String EXTRA_OFFSET =
            "com.example.androidhamradio.EXTRA_OFFSET";
    public static final String EXTRA_OUTPUTFREQ =
            "com.example.androidhamradio.EXTRA_OUTPUTFREQ";
    public static final String EXTRA_STATE =
            "com.example.androidhamradio.EXTRA_STATE";
    public static final String EXTRA_UPLINKTONE =
            "com.example.androidhamradio.EXTRA_UPLINKTONE";
    public static final String EXTRA_USE =
            "com.example.androidhamradio.EXTRA_USE";

    // variables required to create Java reference
    private EditText editTextCall;
    private EditText editTextCounty;
    private EditText editTextDownlinkTone;
    private EditText editTextInputFreq;
    private EditText editTextLocation;
    private EditText editTextOffset;
    private EditText editTextOutputFreq;
    private EditText editTextState;
    private EditText editTextUplinkTone;
    private EditText editTextUse;

    // references needed for preferences
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;

    // integer variables to hold TextColor
    private int defaultCallSignColor;
    private int defaultPrefsSignColor;

    //needed to start activity from onClick
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repeater);

        // create Java object for GUI
        editTextCall = findViewById(R.id.edit_text_call);
        editTextCounty = findViewById(R.id.edit_text_county);
        editTextDownlinkTone = findViewById(R.id.edit_text_downlinktone);
        editTextInputFreq = findViewById(R.id.edit_text_inputfreq);
        editTextLocation = findViewById(R.id.edit_text_location);
        editTextOffset = findViewById(R.id.edit_text_offset);
        editTextOutputFreq = findViewById(R.id.edit_text_outputfreq);
        editTextState = findViewById(R.id.edit_text_state);
        editTextUplinkTone = findViewById(R.id.edit_text_uplinktone);
        editTextUse = findViewById(R.id.edit_text_use);

        // call testData method, it populates EditTexts with test data
        testData();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        // get required intent
        Intent intent = getIntent();

        // check if this is an edit action
        if (intent.hasExtra(EXTRA_ID))
            {
            setTitle("Edit Repeater");

            // populate editText with data
            editTextCall.setText(intent.getStringExtra(EXTRA_CALL));
            editTextCounty.setText(intent.getStringExtra(EXTRA_COUNTY));
            editTextDownlinkTone.setText(intent.getStringExtra(EXTRA_DOWNLINKTONE));
            editTextInputFreq.setText(intent.getStringExtra(EXTRA_INPUTFREQ));
            editTextLocation.setText(intent.getStringExtra(EXTRA_LOCATION));
            editTextOffset.setText(intent.getStringExtra(EXTRA_OFFSET));
            editTextOutputFreq.setText(intent.getStringExtra(EXTRA_OUTPUTFREQ));
            editTextState.setText(intent.getStringExtra(EXTRA_STATE));
            editTextUplinkTone.setText(intent.getStringExtra(EXTRA_UPLINKTONE));
            editTextUse.setText(intent.getStringExtra(EXTRA_USE));
            }
        else
            {
            // this is an add function
            setTitle("Add Repeater");
            }

        // set up preferences in private mode
        preferenceSettings = getSharedPreferences("PREFS", PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        // get the data from the preferences with default if no preference
        defaultCallSignColor = preferenceSettings.getInt("CALLSIGNFONTCOLORINT", 0);
        defaultPrefsSignColor = preferenceSettings.getInt("PARAMFONTCOLORINT", 0);
        // set the default tip in the seek bar
        if (defaultCallSignColor == 0)
        {
            editTextCall.setTextColor(Color.BLACK);
        }
        else if (defaultCallSignColor == 1)
        {
            editTextCall.setTextColor(Color.RED);
        }
        else if (defaultCallSignColor == 2)
        {
            editTextCall.setTextColor(Color.MAGENTA);
        }
        else if (defaultCallSignColor == 3)
        {
            editTextCall.setTextColor(Color.GREEN);
        }
        else
        {
            editTextCall.setTextColor(Color.BLUE);
        }

        if (defaultPrefsSignColor == 0)
        {
            editTextState.setTextColor(Color.BLACK);
            editTextCounty.setTextColor(Color.BLACK);
            editTextLocation.setTextColor(Color.BLACK);
            editTextInputFreq.setTextColor(Color.BLACK);
            editTextOutputFreq.setTextColor(Color.BLACK);
            editTextUplinkTone.setTextColor(Color.BLACK);
            editTextDownlinkTone.setTextColor(Color.BLACK);
            editTextOffset.setTextColor(Color.BLACK);
            editTextUse.setTextColor(Color.BLACK);
        }
        else if (defaultPrefsSignColor == 1)
        {
            editTextState.setTextColor(Color.RED);
            editTextCounty.setTextColor(Color.RED);
            editTextLocation.setTextColor(Color.RED);
            editTextInputFreq.setTextColor(Color.RED);
            editTextOutputFreq.setTextColor(Color.RED);
            editTextUplinkTone.setTextColor(Color.RED);
            editTextDownlinkTone.setTextColor(Color.RED);
            editTextOffset.setTextColor(Color.RED);
            editTextUse.setTextColor(Color.RED);
        }
        else if (defaultPrefsSignColor == 2)
        {
            editTextState.setTextColor(Color.MAGENTA);
            editTextCounty.setTextColor(Color.MAGENTA);
            editTextLocation.setTextColor(Color.MAGENTA);
            editTextInputFreq.setTextColor(Color.MAGENTA);
            editTextOutputFreq.setTextColor(Color.MAGENTA);
            editTextUplinkTone.setTextColor(Color.MAGENTA);
            editTextDownlinkTone.setTextColor(Color.MAGENTA);
            editTextOffset.setTextColor(Color.MAGENTA);
            editTextUse.setTextColor(Color.MAGENTA);
        }
        else if (defaultPrefsSignColor == 3)
        {
            editTextState.setTextColor(Color.GREEN);
            editTextCounty.setTextColor(Color.GREEN);
            editTextLocation.setTextColor(Color.GREEN);
            editTextInputFreq.setTextColor(Color.GREEN);
            editTextOutputFreq.setTextColor(Color.GREEN);
            editTextUplinkTone.setTextColor(Color.GREEN);
            editTextDownlinkTone.setTextColor(Color.GREEN);
            editTextOffset.setTextColor(Color.GREEN);
            editTextUse.setTextColor(Color.GREEN);
        }
        else
        {
            editTextState.setTextColor(Color.BLUE);
            editTextCounty.setTextColor(Color.BLUE);
            editTextLocation.setTextColor(Color.BLUE);
            editTextInputFreq.setTextColor(Color.BLUE);
            editTextOutputFreq.setTextColor(Color.BLUE);
            editTextUplinkTone.setTextColor(Color.BLUE);
            editTextDownlinkTone.setTextColor(Color.BLUE);
            editTextOffset.setTextColor(Color.BLUE);
            editTextUse.setTextColor(Color.BLUE);
        }
    }

    // saveRepeater method
    private void saveRepeater()
    {

        // get input from EditText fields
        String call = editTextCall.getText().toString();
        String county = editTextCounty.getText().toString();
        String downlinkTone = editTextDownlinkTone.getText().toString();
        String inputFreq = editTextInputFreq.getText().toString();
        String location = editTextLocation.getText().toString();
        String offset = editTextOffset.getText().toString();
        String outputFreq = editTextOutputFreq.getText().toString();
        String state = editTextState.getText().toString();
        String uplinkTone = editTextUplinkTone.getText().toString();
        String use = editTextUse.getText().toString();

        // all EditText fields must be populated with data, if not display toast indicating missing data
        if (call.trim().isEmpty() || county.trim().isEmpty() || downlinkTone.trim().isEmpty()
                || inputFreq.trim().isEmpty() || location.trim().isEmpty()
                || offset.trim().isEmpty() || outputFreq.trim().isEmpty() || state.trim().isEmpty()
                || uplinkTone.trim().isEmpty() || use.trim().isEmpty()

        )
        {
            Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
            // return stops Intent below from being executed
            return;
        }

        // Intent to send data back to MainActivity that spawned AddEditRepeaterActivity
        Intent data = new Intent();
        data.putExtra(EXTRA_CALL, call);
        data.putExtra(EXTRA_COUNTY, county);
        data.putExtra(EXTRA_DOWNLINKTONE, downlinkTone);
        data.putExtra(EXTRA_INPUTFREQ, inputFreq);
        data.putExtra(EXTRA_LOCATION, location);
        data.putExtra(EXTRA_OFFSET, offset);
        data.putExtra(EXTRA_OUTPUTFREQ, outputFreq);
        data.putExtra(EXTRA_STATE, state);
        data.putExtra(EXTRA_UPLINKTONE, uplinkTone);
        data.putExtra(EXTRA_USE, use);

        // id is required to find which repeater is being updated
        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        // if id is not -1 put it in intent
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        // used to indicate if the input was successful
        setResult(RESULT_OK, data);
        // close activity
        finish();
    }

    // add menu to Activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_repeater_menu, menu);
        return true;
    }

    // determine which menu item was selected, take appropriate action
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.save_repeater:
                // call saveRepeater method
                saveRepeater();
                return true;
            case R.id.update_pref:
                // create new Intent
                Intent intent = new Intent(context, PreferenceActivity.class);

                //start the activity
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // method generates test data
    public void testData()
    {

        // Creates new random number generator
        Random r = new Random();

        // fill EditText fields with unique random data based on random number generator
        editTextCall.setText("N8BC" + (String.valueOf(r.nextInt(99))));

        String[] stateArr = {"Ohio","Pennsylvania","Michigan","Indiana","Illinois","Tennessee"};
        editTextState.setText(stateArr[r.nextInt(5)]);

        String[] countyArr = {"Lorain","Holmes","Guernsey","Ashtabula","Athens","Huron"};
        editTextCounty.setText(countyArr[r.nextInt(5)]);

        String[] cityArr = {"Mentor","Columbus","Cincinnati","Sandusky","Toledo","Mansfield"};
        editTextLocation.setText(cityArr[r.nextInt(5)]);

        Double dblInputFreq = 147+(r.nextInt(499)*0.0001);

        editTextInputFreq.setText(String.format("%.3f", dblInputFreq));

        Double dblOutputFreq = 147+((r.nextInt(499)+500)*0.0001);

        editTextOutputFreq.setText(String.format("%.3f", dblOutputFreq));

        editTextUplinkTone.setText(String.valueOf(110+(r.nextInt(5)*0.1)));

        editTextDownlinkTone.setText(String.valueOf(110+((r.nextInt(5)+5)*0.1)));

        Boolean blOffset = r.nextBoolean();

        if (blOffset)
            {
                editTextOffset.setText("+");
            }
        else
            {
                editTextOffset.setText("-");
            }

        Boolean blUse = r.nextBoolean();

        if (blUse)
            {
                editTextUse.setText("OPEN");
            }
        else
            {
                editTextUse.setText("CLOSED");
            }
    }

    // lifecycle methods
    @Override
    public void onStart()
    {
        super.onStart();
        System.out.println("MainActivity onStart");
        // get the data from the preferences with default if no preference
        defaultCallSignColor = preferenceSettings.getInt("CALLSIGNFONTCOLORINT", 0);
        defaultPrefsSignColor = preferenceSettings.getInt("PARAMFONTCOLORINT", 0);
        // set the default tip in the seek bar
        if (defaultCallSignColor == 0)
        {
            editTextCall.setTextColor(Color.BLACK);
        }
        else if (defaultCallSignColor == 1)
        {
            editTextCall.setTextColor(Color.RED);
        }
        else if (defaultCallSignColor == 2)
        {
            editTextCall.setTextColor(Color.MAGENTA);
        }
        else if (defaultCallSignColor == 3)
        {
            editTextCall.setTextColor(Color.GREEN);
        }
        else
        {
            editTextCall.setTextColor(Color.BLUE);
        }


        if (defaultPrefsSignColor == 0)
        {
            editTextState.setTextColor(Color.BLACK);
            editTextCounty.setTextColor(Color.BLACK);
            editTextLocation.setTextColor(Color.BLACK);
            editTextInputFreq.setTextColor(Color.BLACK);
            editTextOutputFreq.setTextColor(Color.BLACK);
            editTextUplinkTone.setTextColor(Color.BLACK);
            editTextDownlinkTone.setTextColor(Color.BLACK);
            editTextOffset.setTextColor(Color.BLACK);
            editTextUse.setTextColor(Color.BLACK);
        }
        else if (defaultPrefsSignColor == 1)
        {
            editTextState.setTextColor(Color.RED);
            editTextCounty.setTextColor(Color.RED);
            editTextLocation.setTextColor(Color.RED);
            editTextInputFreq.setTextColor(Color.RED);
            editTextOutputFreq.setTextColor(Color.RED);
            editTextUplinkTone.setTextColor(Color.RED);
            editTextDownlinkTone.setTextColor(Color.RED);
            editTextOffset.setTextColor(Color.RED);
            editTextUse.setTextColor(Color.RED);
        }
        else if (defaultPrefsSignColor == 2)
        {
            editTextState.setTextColor(Color.MAGENTA);
            editTextCounty.setTextColor(Color.MAGENTA);
            editTextLocation.setTextColor(Color.MAGENTA);
            editTextInputFreq.setTextColor(Color.MAGENTA);
            editTextOutputFreq.setTextColor(Color.MAGENTA);
            editTextUplinkTone.setTextColor(Color.MAGENTA);
            editTextDownlinkTone.setTextColor(Color.MAGENTA);
            editTextOffset.setTextColor(Color.MAGENTA);
            editTextUse.setTextColor(Color.MAGENTA);
        }
        else if (defaultPrefsSignColor == 3)
        {
            editTextState.setTextColor(Color.GREEN);
            editTextCounty.setTextColor(Color.GREEN);
            editTextLocation.setTextColor(Color.GREEN);
            editTextInputFreq.setTextColor(Color.GREEN);
            editTextOutputFreq.setTextColor(Color.GREEN);
            editTextUplinkTone.setTextColor(Color.GREEN);
            editTextDownlinkTone.setTextColor(Color.GREEN);
            editTextOffset.setTextColor(Color.GREEN);
            editTextUse.setTextColor(Color.GREEN);
        }
        else
        {
            editTextState.setTextColor(Color.BLUE);
            editTextCounty.setTextColor(Color.BLUE);
            editTextLocation.setTextColor(Color.BLUE);
            editTextInputFreq.setTextColor(Color.BLUE);
            editTextOutputFreq.setTextColor(Color.BLUE);
            editTextUplinkTone.setTextColor(Color.BLUE);
            editTextDownlinkTone.setTextColor(Color.BLUE);
            editTextOffset.setTextColor(Color.BLUE);
            editTextUse.setTextColor(Color.BLUE);
        }
    }

}
