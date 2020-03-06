// Project:     Capstone Android Project
// Author:      John Diczhazy
// File:        RepeaterAdapter.java
// Description: RepeaterAdapter class is used to populate repeater data into RecyclerView list.

package com.example.androidhamradio;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

// RepeaterAdapter passes ListAdapter inner class with RepeaterAdapter.RepeaterHolder so RecyclerView
//  knows this is the RepeaterHolder we want to use.
public class RepeaterAdapter extends ListAdapter<Repeater, RepeaterAdapter.RepeaterHolder>
{
    // required member variable
    private OnItemClickListener listener;

    public RepeaterAdapter() {
        super(DIFF_CALLBACK);
    }

    // DiffUtil is used to only update items in RecyclerView that have changed
    private static final DiffUtil.ItemCallback<Repeater> DIFF_CALLBACK = new DiffUtil.ItemCallback<Repeater>()
    {
        // comparison logic used to determine if items are the same entry
        @Override
        public boolean areItemsTheSame(Repeater oldItem, Repeater newItem)
        {
            return oldItem.getId() == newItem.getId();
        }

        // all three comparisons must be true for method to return true
        @Override
        public boolean areContentsTheSame(Repeater oldItem, Repeater newItem)
        {
            return oldItem.getCall().equals(newItem.getCall()) &&
                    oldItem.getLocation().equals(newItem.getLocation()) &&
                    oldItem.getState().equals(newItem.getState());
        }
    };


    // RecyclerView Adapter onCreateViewHolder method, RepeaterHolder is automatically used in these
    //  methods
    // Used to create an return a ViewHolder
    @NonNull
    @Override
    public RepeaterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repeater_item, parent, false);
        return new RepeaterHolder(itemView);
    }

    // RecyclerView Adapter onBindViewHolder method, RepeaterHolder is automatically used in these
    //  methods
    // We use this to get the data from a single Repeater Java object into the Views of our RepeaterHolder
    @Override
    public void onBindViewHolder(@NonNull RepeaterHolder holder, int position)
    {
        // Get reference to Repeater object at current position
        Repeater currentRepeater = getItem(position);

        // Place data into TextViews
        holder.textViewCall.setText(currentRepeater.getCall());
        holder.textViewLocation.setText(currentRepeater.getLocation());
        holder.textViewState.setText(currentRepeater.getState());

        // Assign TextColor to TextViews based on MainActivity static variable
        if (MainActivity.CALLSIGNFONTCOLORPUBLICINT == 0)
        {
            holder.textViewCall.setTextColor(Color.BLACK);
        }
        else if (MainActivity.CALLSIGNFONTCOLORPUBLICINT == 1)
        {
            holder.textViewCall.setTextColor(Color.RED);
        }
        else if (MainActivity.CALLSIGNFONTCOLORPUBLICINT == 2)
        {
            holder.textViewCall.setTextColor(Color.MAGENTA);
        }
        else if (MainActivity.CALLSIGNFONTCOLORPUBLICINT == 3)
        {
            holder.textViewCall.setTextColor(Color.GREEN);
        }
        else
        {
            holder.textViewCall.setTextColor(Color.BLUE);
        }

        // Assign TextColor to TextViews based on MainActivity static variable
        if (MainActivity.PARAMFONTCOLORPUBLICINT == 0)
        {
            holder.textViewLocation.setTextColor(Color.BLACK);
            holder.textViewState.setTextColor(Color.BLACK);
        }
        else if (MainActivity.PARAMFONTCOLORPUBLICINT == 1)
        {
            holder.textViewLocation.setTextColor(Color.RED);
            holder.textViewState.setTextColor(Color.RED);
        }
        else if (MainActivity.PARAMFONTCOLORPUBLICINT == 2)
        {
            holder.textViewLocation.setTextColor(Color.MAGENTA);
            holder.textViewState.setTextColor(Color.MAGENTA);
        }
        else if (MainActivity.PARAMFONTCOLORPUBLICINT == 3)
        {
            holder.textViewLocation.setTextColor(Color.GREEN);
            holder.textViewState.setTextColor(Color.GREEN);
        }
        else
        {
            holder.textViewLocation.setTextColor(Color.BLUE);
            holder.textViewState.setTextColor(Color.BLUE);
        }
    }

    // get repeater at RecyclerView position
    public Repeater getRepeaterAt(int position) {
        return getItem(position);
    }

    // Create ViewHolder class, will hold Views in our single RecyclerView items
    class RepeaterHolder extends RecyclerView.ViewHolder {

        //create variables for our three different views
        private TextView textViewCall;
        private TextView textViewLocation;
        private TextView textViewState;

        // RepeaterHolder constructor, the three TextViews are assigned from itemView
        public RepeaterHolder(View itemView)
        {
            super(itemView);
            textViewCall = itemView.findViewById(R.id.text_view_call);
            textViewLocation = itemView.findViewById(R.id.text_view_location);
            textViewState = itemView.findViewById(R.id.text_view_state);

            // OnClickListener, senses which repeater was selected in RecyclerView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    // check if both listener and RecyclerView are not null
                    if (listener != null && position != RecyclerView.NO_POSITION)
                    {
                        // get repeater position in RecyclerView
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    // interface required for repeater update capability
    public interface OnItemClickListener
    {
        // return onItemClick method
        void onItemClick(Repeater repeater);
    }

    // need reference to call OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
