package com.example.cdunkl200.mobile_coursework;

/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Callum Dunkley
// Student ID           S1510033
// Programme of Study   Computing
//

// Update the package name to include your Student Identifier


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import com.example.cdunkl200.mobile_coursework.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private String url1 = "";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    ArrayList<Earthquake> earthquakes = null;
    ArrayList<Earthquake> adaperQuakes = null;
    ArrayList<Earthquake> displayQuakes = null;
    earthquakeAdaptor adapter = null;

    SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adaperQuakes = new ArrayList<>();

        displayQuakes = new ArrayList<>();


        ListView listView=(ListView)findViewById(R.id.earthquakeList);
        searchView = (SearchView) findViewById(R.id.mySearchView);

        earthquakes = new ArrayList<>();

        try {
            Exception str_result= new ProcessInBackground().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        displayQuakes = populateEarthquakeData(displayQuakes);



        System.out.println(displayQuakes);
        adapter = new earthquakeAdaptor(this, displayQuakes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
                return false;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                // do something
                Intent intent=new Intent(getApplicationContext(), QuakeMapsActivity.class);
                intent.putExtra("StringKey", earthquakes);
                startActivity(intent);
                return true;
            case R.id.north:
                // do something
                Earthquake north = earthquakes.get(0);

                for (int i=1;i<earthquakes.size();i++) {
                    if(earthquakes.get(i).getLat() > north.getLat()) {
                        north = earthquakes.get(i);
                    }
                }
                String norths = north.getLocation();

                MainActivity.this.adapter.getFilter().filter(norths);
                return true;
            case R.id.south:
                // do something
                Earthquake south = earthquakes.get(0);

                for (int i=1;i<earthquakes.size();i++) {
                    if(earthquakes.get(i).getLat() < south.getLat()) {
                        south = earthquakes.get(i);
                    }
                }
                String souths = south.getLocation();

                MainActivity.this.adapter.getFilter().filter(souths);
                return true;
            case R.id.east:
                // do something
                Earthquake east = earthquakes.get(0);

                for (int i=1;i<earthquakes.size();i++) {
                    if(earthquakes.get(i).getLong() > east.getLong()) {
                        east = earthquakes.get(i);
                    }
                }
                String easts = east.getLocation();

                MainActivity.this.adapter.getFilter().filter(easts);
                return true;
            case R.id.west:
                // do something
                Earthquake west = earthquakes.get(0);

                for (int i=1;i<earthquakes.size();i++) {
                    if(earthquakes.get(i).getLong() < west.getLong()) {
                        west = earthquakes.get(i);
                    }
                }
                String wests = west.getLocation();

                MainActivity.this.adapter.getFilter().filter(wests);

                return true;
            case R.id.highestMag:
                // do something
                Double max = Double.MIN_VALUE;
                for(int i=0; i<earthquakes.size(); i++){
                    if(earthquakes.get(i).getMagnitude() > max){
                        max = earthquakes.get(i).getMagnitude();
                    }
                }
                String s = Double.toString(max);

                MainActivity.this.adapter.getFilter().filter(s);
                return true;
            case R.id.deepest:
                // do something
                Earthquake smallest = earthquakes.get(0);

                for (int i=1;i<earthquakes.size();i++) {
                    if(earthquakes.get(i).getDepth() > smallest.getDepth()) {
                        smallest = earthquakes.get(i);
                    }
                }
                String deep = smallest.getLocation();

                MainActivity.this.adapter.getFilter().filter(deep);
                return true;
            case R.id.shallowest:
                // do something

                Earthquake shallowest = earthquakes.get(0);

                for (int i=1;i<earthquakes.size();i++) {
                    if(earthquakes.get(i).getDepth() < shallowest.getDepth()) {
                        shallowest = earthquakes.get(i);
                    }
                }
                String shallow = shallowest.getLocation();

                MainActivity.this.adapter.getFilter().filter(shallow);
                return true;
            default:
                return super.onContextItemSelected(item);
        }




    }

    private ArrayList<Earthquake> populateEarthquakeData(ArrayList<Earthquake> displayQuakes) {

       displayQuakes.addAll(earthquakes);
       return displayQuakes;
    }
    public InputStream getInputStream(URL url)
    {
        try
        {
            //openConnection() returns instance that represents a connection to the remote object referred to by the URL
            //getInputStream() returns a stream that reads from the open connection
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                URL url = new URL(urlSource);

                //creates new instance of PullParserFactory that can be used to create XML pull parsers
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                //Specifies whether the parser produced by this factory will provide support
                //for XML namespaces
                factory.setNamespaceAware(false);

                //creates a new instance of a XML pull parser using the currently configured
                //factory features
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");

                /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
                 * We should take into consideration that the rss feed name is also enclosed in a "<title>" tag.
                 * Every feed begins with these lines: "<channel><title>Feed_Name</title> etc."
                 * We should skip the "<title>" tag which is a child of "<channel>" tag,
                 * and take into consideration only the "<title>" tag which is a child of the "<item>" tag
                 *
                 * In order to achieve this, we will make use of a boolean variable called "insideItem".
                 */
                boolean insideItem = false;
                Earthquake newQuake = null;
                // Returns the type of current event: START_TAG, END_TAG, START_DOCUMENT, END_DOCUMENT etc..
                int eventType = xpp.getEventType(); //loop control variable
                while (eventType != XmlPullParser.END_DOCUMENT)
                {


                    //if we are at a START_TAG (opening tag)
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        //if the tag is called "item"



                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;

                            newQuake = new Earthquake();
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {

                            if (insideItem) {
                                String title = xpp.nextText();
                                title = title.replace("UK Earthquake alert : M ", "");
                                title = title.replaceFirst(".*?:", "");
                                String whatyouaresearching = title.substring(0, title.lastIndexOf(","));
                                whatyouaresearching = whatyouaresearching.substring(0, whatyouaresearching.lastIndexOf(","));

                                newQuake.setLocation(whatyouaresearching);
                                //System.out.println("CALLUM " + whatyouaresearching);

                            }
                        }
                        //if the tag is called "title"
                        else if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            if (insideItem)
                            {

                                String output = xpp.nextText();
                                output = output.substring(output.indexOf("Depth:"));
                                String[] descSplit = output.split(";");
                                String depth = descSplit[0];
                                String mag = descSplit[1];

                                depth = depth.replace("Depth: ", "");
                                depth = depth.replace(" km", "");
                                if (depth.equals(" ")){
                                    depth = "0";
                                }
                                double quakeDepth = Double.parseDouble(depth);
                                newQuake.setDepth(quakeDepth);

                                mag = mag.replace("Magnitude: ", "");
                                mag = mag.replace(" ", "");
                                double quakeMag = Double.parseDouble(mag);
                                newQuake.setMagnitude(quakeMag);

                                //System.out.println("depth " + depth);
                                //System.out.println("Magnitude " + mag);
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubdate"))
                        {
                            if (insideItem)
                            {
                                String date = xpp.nextText();

                                SimpleDateFormat formatDate=new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
                                try {
                                    Date quakeDate = formatDate.parse(date);
                                   // System.out.println(quakeDate);
                                    newQuake.setQuakeDate(quakeDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("geo:lat"))
                        {
                            if (insideItem)
                            {
                                String stringLat = xpp.nextText();
                                double lat = Double.parseDouble(stringLat);
                                newQuake.setLat(lat);
                               // System.out.println(lat);
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("geo:long"))
                        {
                            if (insideItem)
                            {
                                String stringLong = xpp.nextText();
                                double quakelong = Double.parseDouble(stringLong);
                                newQuake.setLong(quakelong);

                                //System.out.println(quakelong);
                            }
                        }

                    }

                    //if we are at an END_TAG and the END_TAG is called "item"
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                        earthquakes.add(newQuake);
                    }


                    eventType = xpp.next(); //move to next element

                }

                //System.out.println(earthquakes.toString());

            }
            catch (MalformedURLException | XmlPullParserException e)
            {
                exception = e;
            } catch (IOException e)
            {
                exception = e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);



        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


        String pattern = "dd/MM/yyyy HH:mm:ss";

// Create an instance of SimpleDateFormat used for formatting
// the string representation of date according to the chosen pattern
        DateFormat df = new SimpleDateFormat(pattern);

// Get the today date using Calendar object.
        Date dateOfQuake = earthquakes.get(position).getQuakeDate();
// Using DateFormat format method we can create a string
// representation of a date with the defined format.
        String dateOfQuakeString = df.format(dateOfQuake);


        Intent intent = new Intent(getApplicationContext(),activity_earthquakeData.class);
        intent.putExtra("location",earthquakes.get(position).getLocation());
        intent.putExtra("date",dateOfQuakeString);
        intent.putExtra("mag",Double.toString(earthquakes.get(position).getMagnitude()));
        intent.putExtra("depth",Double.toString(earthquakes.get(position).getDepth()));
        intent.putExtra("lat",Double.toString(earthquakes.get(position).getLat()));
        intent.putExtra("long",Double.toString(earthquakes.get(position).getLong()));

        startActivity(intent);
    }

}


    
