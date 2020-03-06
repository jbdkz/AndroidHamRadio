// Project:     Capstone Android Project
// Author:      John Diczhazy
// File:        PreferenceActivity.java
// Description: Preference Activity used to change font colors

package com.example.androidhamradio;

// import required classes or packages
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class PreferenceActivity extends AppCompatActivity
{

    // declare Java references
    private Spinner spCallSignFontColor;
    private Spinner spParamFontColor;
    private Button btnSaveFontColorPref;

    // reference needed for shared preference
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        // create Java objects and tie to UI
        spCallSignFontColor= (Spinner)findViewById(R.id.spCallSignFontColor);
        spParamFontColor= (Spinner)findViewById(R.id.spParamFontColor);
        btnSaveFontColorPref = (Button)findViewById(R.id.btnSaveFontColorPref);

        // set up preferences with name and in private mode
        preferenceSettings = getSharedPreferences("PREFS", PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        // get the data from the shared preference with default
        spCallSignFontColor.setSelection(preferenceSettings.getInt("CALLSIGNFONTCOLORINT", 0));
        spParamFontColor.setSelection(preferenceSettings.getInt("PARAMFONTCOLORINT", 0));

        // set save preferences onClickListener
        btnSaveFontColorPref.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                // set the preferences
                preferenceEditor.putInt("CALLSIGNFONTCOLORINT", spCallSignFontColor.getSelectedItemPosition());
                preferenceEditor.putInt("PARAMFONTCOLORINT", spParamFontColor.getSelectedItemPosition());
                preferenceEditor.apply();

                // finish the activity
                finish();

                // call toast function to confirm preferences saved
                toastAlert();

            }
        });
    }

    // toast function
    private void toastAlert()
    {
        // present toast message
        Toast.makeText(this, "Preferences Saved!", Toast.LENGTH_SHORT).show();
    }
}
