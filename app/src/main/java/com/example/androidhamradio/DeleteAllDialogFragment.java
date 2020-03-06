// Project:     Capstone Android Project
// Author:      John Diczhazy
// File:        DeleteAllDialogFragment.java
// Description: Delete all Repeaters confirmation

package com.example.androidhamradio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

// this class represents the missile dialog
public class DeleteAllDialogFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set the Message
        builder.setMessage(R.string.dialog_delete_all_message);

        // set the title
        builder.setTitle("Delete Confirmation");

        // set the positive button
        builder.setPositiveButton(R.string.btn_delete_all, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // Get the main activity, not needed in this example
                MainActivity ma = (MainActivity)getActivity();

                // call the listener method in the main activity
                mListener.onDeleteAllDialogPositiveClick();
            }
        });

        // set the negative button
        builder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
                // call the listener method in the main activity
                mListener.onDeleteAllDialogNegativeClick();
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    // public interface used to define callback methods
    // that will be called in the MainActivity
    public interface ResetDialogListener
    {
        // these method will be implemented in the MainActivity
        public void onDeleteAllDialogPositiveClick();
        public void onDeleteAllDialogNegativeClick();
    }

    // Use this instance of the interface to deliver action events
    private ResetDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    //  public void onAttach(Activity activity) // is deprecated
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try
        {
            // Instantiate the ResetDialogListener so we can send events to the host
            mListener = (ResetDialogListener) context;
        }
        catch (ClassCastException e)
        {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement DeleteAllDialogListener");
        }
    }
}
