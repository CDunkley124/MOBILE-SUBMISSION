//
// Name                 Callum Dunkley
// Student ID           S1510033
// Programme of Study   Computing
//

package com.example.cdunkl200.mobile_coursework;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class earthquakeAdaptor extends ArrayAdapter<Earthquake> {

    ArrayList<Earthquake> quakes, tempQuakes, suggestions;

    public earthquakeAdaptor(Context context, ArrayList<Earthquake> objects)
    {
    super(context, R.layout.list_item, R.id.location, objects);
    this.quakes = objects;
    this.tempQuakes = new ArrayList<Earthquake>(objects);
    this.suggestions = new ArrayList<Earthquake>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        Earthquake earthquake = getItem(position);

        if(convertView == null) {
            if(parent == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
            else
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView txtQuake = (TextView) convertView.findViewById(R.id.location);
        if (txtQuake != null)
            txtQuake.setText(earthquake.getLocation());

        TextView txtMag = (TextView) convertView.findViewById(R.id.magnitude);
        if (txtMag != null)
            txtMag.setText(Double.toString(earthquake.getMagnitude()));

        LinearLayout lay = convertView.findViewById(R.id.magLayout);
        if (earthquake.getMagnitude() >= -1 && earthquake.getMagnitude() < 0.5) {
            lay.setBackgroundColor(Color.GREEN);
        } else if (earthquake.getMagnitude() >= 0.5 && earthquake.getMagnitude() < 1.5) {
            lay.setBackgroundColor(Color.YELLOW);
        } else if (earthquake.getMagnitude() >= 1.5 && earthquake.getMagnitude() < 2) {
            lay.setBackgroundColor(Color.rgb(255, 123, 0));
        } else if (earthquake.getMagnitude() >= 2) {
            lay.setBackgroundColor(Color.RED);
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }
    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Earthquake earthquake = (Earthquake) resultValue;
            return earthquake.getLocation();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Earthquake quake : tempQuakes) {
                    if (quake.getLocation().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(quake);
                    }
                    if (Double.toString(quake.getMagnitude()).startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(quake);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

            @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Earthquake> e = (ArrayList<Earthquake>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Earthquake quake : e) {
                    add(quake);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };

}
