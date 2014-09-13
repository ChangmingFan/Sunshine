
package com.example.android.sunshine.app.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class WeatherContract {
    //Content provider name: CONTENT_AUTHORITY = app's package name; used to create base of all URI
    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WEATHER = "weather";
    public static final String PATH_LOCATION = "location";
    /* Inner class that defines the table contents of the location table */
    public static final class LocationEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;


        // Table name
        public static final String TABLE_NAME = "location";

        // The location setting string is what will be sent to openweathermap
        // as the location query.
        public static final String COLUMN_LOCATION_SETTING = "location_setting";

        // Human readable location string, provided by the API.  Because for styling,
        // "Mountain View" is more recognizable than 94043.
        public static final String COLUMN_CITY_NAME = "city_name";

        // In order to uniquely pinpoint the location on the map when we launch the
        // map intent, we store the latitude and longitude as returned by openweathermap.
        public static final String COLUMN_COORD_LAT = "coord_lat";
        public static final String COLUMN_COORD_LONG = "coord_long";
    }

    /* Inner class that defines the table contents of the weather table */
    public static final class WeatherEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;

        public static final String TABLE_NAME = "weather";

        // Column with the foreign key into the location table.
        public static final String COLUMN_LOC_KEY = "location_id";
        // Date, stored as Text with format yyyy-MM-dd
        public static final String COLUMN_DATETEXT = "date";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_WEATHER_ID = "weather_id";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_SHORT_DESC = "short_desc";

        // Min and max temperatures for the day (stored as floats)
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";

        // Humidity is stored as a float representing percentage
        public static final String COLUMN_HUMIDITY = "humidity";

        // Humidity is stored as a float representing percentage
        public static final String COLUMN_PRESSURE = "pressure";

        // Windspeed is stored as a float representing windspeed  mph
        public static final String COLUMN_WIND_SPEED = "wind";

        // Degrees are meteorological degrees (e.g, 0 is north, 180 is south).  Stored as floats.
        public static final String COLUMN_DEGREES = "degrees";

        public static Uri buildWeatherUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildWeatherLocation(String locationSetting){
            return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
        }

        public static Uri buildWeatherLocationWithStartDate(
                String locationSetting, String startDate){
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendQueryParameter(COLUMN_DATETEXT,startDate).build();
        }

        public static Uri buildWeatherLocationWithDate(String locationSetting, String date){
            return CONTENT_URI.buildUpon().appendPath(locationSetting).appendPath(date).build();
        }
        // helper functions to help decode URI structure
        public static String getLocationSettingFromUri(Uri uri){
            return uri.getPathSegments().get(1);
        }

        public static String getDateFromUri(Uri uri){
            return uri.getPathSegments().get(2);
        }

        public static String getStartDateFromUri(Uri uri){
                return uri.getQueryParameter(COLUMN_DATETEXT);
        }
    }
}








/*  The following created 083024, with error static not allowed ??
// ---start
package data;

*/
/*
 * Created by Changming on 8/30/2014. Not yet commit
 *//*


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

*/
/**
 * ContentProvider : table and column names for the weather database.
 *//*

*/
/*  083024 to match 4.05
public class WeatherContract {
    // Name of the ContentProvider = CONTENT_AUTHORITY
    public static  final  String CONTENT_AUTHORITY = "com.example.android.sunshine.app";
    // URI for apps to contact ContentProvider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // Possible paths:
    public  static  final String PATH_WEATHER = "weather";
    public  static  final String PATH_LOCATION = "location";
*//*


    /////////////////
    // Table contents - Inner class
    // Table 1/2
    // location table
    public static final class LocationEntry implements BaseColumns{
        */
/*public static  final  Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_LOCATION).build();
        public static  final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        *//*

        // Table Name:
        public  static  final  String TABLE_NAME = "location";
        // Location setting to be sent to open weather map - location query
        public  static  final  String COLUMN_LOCATION_SETTING = "location_settting";
        // Location string: provide by API, eg 94043 = Mountain View, 48104 = Ann Arbor, etc.
        public  static  final  String  COLUMN_CITY_NAME = "city_name";
        // Latitude-Longitude by open weather map
        public  static  final  String COLUMN_CORD_LAT = "coord_lat";
        public  static  final  String COLUMN_CORD_LONG = "coord_long";

        */
/*public  static  Uri buildLocationUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }*//*

    }
    // Table 2/2 - Inner class
    // weather table
    public static final class WeatherEntry implements BaseColumns {
        */
/*public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_WEATHER).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;*//*


        // Table Name:
        public static final String TABLE_NAME = "weather";

        // Column with the foreign key into the location table.
        // 10 columns:
        // location_id date weather_id short_desc min max humidity pressure wind degrees
        public  static  final  String COLUMN_LOC_KEY = "location_id";
        public  static  final  String COLUMN_DATETEXT = "date";
        public  static  final  String COLUMN_WEATHER_ID = "weather_id";
        public  static  final  String SHORT_DESC = "short_desc";
        public  static  final  String COLUMN_MIN_TEMP = "min";
        public  static  final  String COLUMN_MAX_TEMP = "max";
        public  static  final  String COLUMN_HUMIDITY = "humidity";
        public  static  final  String COLUMN_PRESSURE = "pressure";
        public  static  final  String COLUMN_WIND_SPEED = "wind";
        public  static  final  String COLUMN_DEGREES = "degrees"; //(North=0; South=180)

        // 4 builds: Uri, Location, StartDate, Date.
        */
/*public  static  Uri buildWeatherUri(long id){
            return  ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public  static  Uri buildWeatherLocation(
                String locationSetting){
            return  CONTENT_URI.buildUpon().appendPath(locationSetting).build();
        }

        public  static  Uri buildWeatherLocationWithStartDate(
                String locationSetting, String  startDate){
            return  CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendQueryParameter(COLUMN_DATETEXT, startDate).build();
        }

        public  static  Uri buildWeatherLocationWithDate(
                String locationSetting, String  date){
            return  CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendPath(date).build();
        }*//*


        // 3 gets: LocationSetting, Date, StartDate.
        public  static  String  getLocationSettingFromUri(Uri uri){
            return  uri.getPathSegments().get(1);
        }
        public  static  String  getDateFromUri(Uri uri){
            return  uri.getPathSegments().get(2);
        }

        public  static  String  getStartDateFromUri(Uri uri){
            return  uri.getQueryParameter(COLUMN_DATETEXT);
        }
    }

// --end*/
