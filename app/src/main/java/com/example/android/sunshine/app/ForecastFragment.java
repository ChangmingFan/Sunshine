package com.example.android.sunshine.app;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// A placeholder fragment containing a simple view.
public class ForecastFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    // here is empty constructor:
    public ForecastFragment() {
    }
//081514
            /*The date/time conversion code is going to be moved outside the Asycntask later,
        so for convenience we're breaking it out into its own method now.
        */
    public String getReadableDateString(long time){
        // because the API returns a unix timestamp (measure in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E,MMM d");
        return format.format(date).toString();
    }
    /**
     * Prepare the weather high/lows for presentation.
     */
    public String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }


    //081114
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.forecastfragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            //081914-357pm FetchWeatherTask weatherTask = new FetchWeatherTask();

            /* OK till 081614
            //weatherTask.execute("94043");
            //weatherTask.execute("48104");  or-
            //weatherTask.execute("Ann Arbor MI");
            // Also OK -MountainView CA. 081614 2:30PM
            //"city":{"id":"5375480",  "name":"Mountain View","coord":{"lon":-122.087,"lat":37.3837}
            //weatherTask.execute("94040");
            //weatherTask.execute();*/

            // 081714 change to use SharedPreferenceTask ---

            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            //String location = prefs.getString(getString(R.string.pref_location_key),
            //        getString(R.string.pref_location_default));
            //weatherTask.execute(location);
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Declare foreCastAdapter  variable 081114
    in the ForecastFragment class (outside of any method)suggestion 27 Jul, 15:13 Martin-740*/
    //private ArrayAdapter<String> foreCastAdapter;

    private ArrayAdapter<String> mForeCastAdapter;
    // 081514
    // mForecastAdapter is global so I can access it from with FetchWeatherTask.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // The ArrayAdapter will take data a source and
        // use it to populate the ListView it's attached to.
        mForecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(),                     // The current context (this activity
                        R.layout.list_item_forecast,       // Layout ID name
                        R.id.list_item_forecast_textview,  // ID of the textView to populate.
                        new ArrayList<String>());


/*  081914  6:15 Pm Storm is heavy now but OK for all now.
       comment here to use method updateWeather
        //View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Create some dummy data for the ListView. eg. day, whether, high/low
        String[] forecastArray = {
                "Today - SUNNY - 88/63 These are Mock data!!",
                "Tomorrow - Sunny - 70/43",
                "Weds - Cloudy - 77/63",
                "Thurs - Big Storm - 88/63",
                "Fri - Heavy Rain - 60/53",
                "Sat - Help Trapped - 60/61",
                "Sun - Foggy - 88/63"
        };

        List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

        // Now that we have some dummy forecast data, create an ArrayAdapter,
        // The ArrayAdapter will take data from a source (like our dummy forecast
        // use it ti populate the ListView it's attached to.

        //foreCastAdapter =
        //081514

        mForecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(),                     // The current context (this activity
                        R.layout.list_item_forecast,       // ID of list item layout
                        R.id.list_item_forecast_textview,  // ID of the textView to populate.
                        weekForecast);                     // Forecast data




        mForecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(),                     // The current context (this activity
                        R.layout.list_item_forecast,       // ID of list item layout

 here */

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //  Get a reference to the ListView and attach this adapter to ListView
        final ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        //listView.setAdapter(foreCastAdapter);
        //081514
        listView.setAdapter(mForecastAdapter);
        //081614 330PM lesson 3 itemClickListener-Toast
        //listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
        //    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // 081614 5PM


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int iposition, long l) {
                String forecast = mForecastAdapter.getItem(iposition);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        return rootView;
    }


    //081514 Add from gistfield1.java
    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wire frames.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_DATETIME = "dt";
        final String OWM_DESCRIPTION = "main";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        String[] resultStrs = new String[numDays];
        for(int i = 0; i < weatherArray.length(); i++) {
            // For now, using the format "Day, description, hi/low"
            String day;
            String description;
            String highAndLow;

            // Get the JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            // The date/time is returned as a long.  We need to convert that
            // into something human-readable, since most people won't read "1400356800" as
            // "this saturday".
            long dateTime = dayForecast.getLong(OWM_DATETIME);
            day = getReadableDateString(dateTime);

            // description is in a child array called "weather", which is 1 element long.
            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            // Temperatures are in a child object called "temp".  Try not to name variables
            // "temp" when working with temperature.  It confuses everybody.
            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            double high = temperatureObject.getDouble(OWM_MAX);
            double low = temperatureObject.getDouble(OWM_MIN);

            highAndLow = formatHighLows(high, low);
            resultStrs[i] = day + " - " + description + " - " + highAndLow;
        }
/*
        081514 Not sure -- if UnComment, LOG_TAG error
        for (String s : resultStrs) {
            Log.v(LOG_TAG, "Forecast entry: " + s);
        }
*/

        return resultStrs;
    }



    private void updateWeather(){
        FetchWeatherTask weatherTask = new FetchWeatherTask();
        String location = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_location_key),
                    getString(R.string.pref_location_default));
        weatherTask.execute(location);
    }

    @Override
    public void onStart(){
        super.onStart();
        updateWeather();
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
        // FetchWeatherTask cannot be static so able to access
        // the member variable from ForecastFragment
        // This is postal code param, below also use UriBuilder to build up URL
        // public class FetchWeatherTask extends AsyncTask<Void, Void, Void> {
        // just query as input param
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();


        @Override
        protected String[] doInBackground(String... params) {

            // if there's no zip code, there's nothing to look u[. Verify size of params,
            if (params.length == 0){
                return null;
            }
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            //param values declare-define:  081314
            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are awaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");

                //081314 Build Uri below for 94034
                final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(UNITS_PARAM, units)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .build();

                URL url = new URL(builtUri.toString());

                // To verify the above buildup correctly, we add the print out below
                Log.v(LOG_TAG, "Built URI" + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    // forecastJsonStr = null;
                    return null;
                }
                forecastJsonStr = buffer.toString();

                // 081114
                // Log.v(LOG_TAG, "Forecast JSON String     : " + forecastJsonStr);
                // 081614 Success ! UI OK now - after click Refresh  weadther data showed on my Huawei Android phone.
                // Comment the above line - the logcat verbose will not show as it did successfuly-
                //08-16 12:28:56.494    7780-7955/com.example.android.sunshine.app
                // V/FetchWeatherTask﹕ Forecast JSON String     :
                // {"cod":"200","message":0.5747,"city":{"id":"5375480","name":"Mountain View","coord":{"lon":-122.075,


                // try 081614 noon  Not show 48104 ??
                // It Did 2:PM
                Log.v(LOG_TAG, "Forecast JSON String  ---   : " + forecastJsonStr);

            } catch (IOException e) {
                //Log.e("ForecastFragment", "Error ", e);
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                //forecastJsonStr = null;
                //081514
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        //Log.e("ForecastFragment", "Error closing stream", e);
                        // 081514
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try{
                return getWeatherDataFromJson(forecastJsonStr, numDays);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            // add return null Not Sure 081114
            // This will only happen if there was an error getting or parsing the forecast.
            return null;

        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null){
                mForecastAdapter.clear();
                for (String dayForecastStr : result) {
                    mForecastAdapter.add(dayForecastStr);
                }
                //  New data is bac from the server.
            }
        }
//
/*        @Override
        public String toString() {
            return super.toString();
        }*/
    }
}