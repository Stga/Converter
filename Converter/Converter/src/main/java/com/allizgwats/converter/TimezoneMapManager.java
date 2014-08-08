package com.allizgwats.converter;

import android.content.Context;

import java.util.HashMap;

public class TimezoneMapManager {

    private HashMap<String, String[]> timezoneMap = new HashMap<String, String[]>();
    private Context applicationContext;


    public TimezoneMapManager(Context context) {
        this.applicationContext = context;

        //Put the World level data in the map (i.e. World Regions)
        setWorldLevelInMap();

        //Put the Region level data in the map (i.e. Countries)
        setRegionLevelInMap();

        //Put the Country level data in the map (i.e. Cities/Timezones)
        //TODO place country level insertion methods here

    }

    public HashMap<String, String[]> getTimezoneMap() {
        return timezoneMap;
    }

    /**
     * Put the World region String array into the map
     */
    private void setWorldLevelInMap() {
        //Default level
        timezoneMap.put("default", applicationContext.getResources()
                .getStringArray(R.array.timeConverter_timezoneListDefaultLevel));
    }

    /**
     * Put the Region Level String arrays into the map
     */
    private void setRegionLevelInMap() {
        //Africa
        timezoneMap.put("Africa", applicationContext.getResources()
                .getStringArray(R.array.timeConverter_timezoneListAfrica));

        //Americas
        timezoneMap.put("Americas", applicationContext.getResources()
                .getStringArray(R.array.timeConverter_timezoneListAmericas));

        //Asia
        timezoneMap.put("Asia", applicationContext.getResources()
                .getStringArray(R.array.timeConverter_timezoneListAsia));

        //Australia/Oceania
        timezoneMap.put("Australia/Oceania", applicationContext.getResources()
                .getStringArray(R.array.timeConverter_timezoneListAustraliaOceania));

        //Europe
        timezoneMap.put("Europe", applicationContext.getResources()
                .getStringArray(R.array.timeConverter_timezoneListEurope));
    }


    public static void main(String[] args) {
        //String[] tz = java.util.TimeZone.getAvailableIDs();
        java.util.TimeZone t = java.util.TimeZone.getTimeZone("EET");

        /*
        for( String s : tz ) {
            System.out.println(s);
        }
        */

        System.out.println(t.inDaylightTime(new java.util.Date()));

        System.out.println(t.inDaylightTime(new java.util.Date(2014, 10, 10)));
    }

    //TODO write city level string arrays
}
