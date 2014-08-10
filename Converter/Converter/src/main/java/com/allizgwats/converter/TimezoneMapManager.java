package com.allizgwats.converter;

import java.util.HashMap;

public class TimezoneMapManager {

    private HashMap<String, String[]> timezoneMap = new HashMap<String, String[]>();

    public TimezoneMapManager() {

        //Put the World level data in the map (i.e. World Regions)
        setRegionsInMap();

        //Put the Region level data in the map (i.e. Countries)
        setCountriesInMap();

        //Put the Country level data in the map (i.e. Timezones of each country)
        setAfricanTimezonesInMap();
        setAmericasTimezonesInMap();
        setAsianTimezonesInMap();
        setAustralianOceaniaTimezoneInMap();
        setEuropeanTimezoneInMap();
    }

    public HashMap<String, String[]> getTimezoneMap() {
        return timezoneMap;
    }

    /**
     * Put the World regions String array into the map
     */
    private void setRegionsInMap() {
        //Default level
        timezoneMap.put("default", new String[]{
            "Africa", "Americas", "Asia", "Australia/Oceania", "Europe"
        });
    }

    /**
     * Put the Regions Countries String arrays into the map
     */
    private void setCountriesInMap() {
        //Africa
        timezoneMap.put("Africa", new String[]{
            "Algeria"        , "Angola"             , "Benin",
            "Botswana"       , "Burkina Faso"       , "Burundi",
            "Cameroon"       , "Cape Verde"         , "Central African Republic",
            "Chad"           , "Comoros"            , "Congo-Brazzaville",
            "Congo-Kinshasa" , "Cote d\'Ivoire"     , "Djibouti",
            "Egypt"          , "Equatorial Guinea"  , "Eritrea",
            "Ethiopia"       , "Gabon"              , "Gambia",
            "Ghana"          , "Guinea"             , "Guinea-Bissau",
            "Kenya"          , "Lesotho"            , "Liberia",
            "Libya"          , "Madagascar"         , "Malawi",
            "Mali"           , "Mauritania"         , "Mauritius",
            "Morocco"        , "Mozambique"         , "Namibia",
            "Niger"          , "Nigeria"            , "Rwanda",
            "Senegal"        , "Sierra Leone"       , "Somalia",
            "South Africa"   , "South Sudan"        , "Sudan",
            "Swaziland"      , "Tanzania"           , "Togo",
            "Tunisia"        , "Uganda"             , "Zambia",
            "Zimbabwe"
        });

        //Americas
        timezoneMap.put("Americas", new String[]{
            "Anguilla"              , "Antigua and Barbuda"              , "Argentina",
            "Aruba"                 , "Bahamas"                          , "Barbados",
            "Belize"                , "Bermuda"                          , "Bolivia",
            "Brazil"                , "British Virgin Islands"           , "Canada",
            "Cayman Islands"        , "Chile"                            , "Colombia",
            "Costa Rica"            , "Cuba"                             , "Dominica",
            "Dominican Republic"    , "Ecuador"                          , "El Salvador",
            "French Guiana"         , "Grenada"                          , "Guadeloupe",
            "Guatemala"             , "Guyana"                           , "Haiti",
            "Honduras"              , "Jamaica"                          , "Martinique",
            "Mexico"                , "Montserrat"                       , "Netherlands Antilles",
            "Nicaragua"             , "Panama"                           , "Paraguay",
            "Peru"                  , "Puerto Rico"                      , "Saint Kitts and Nevis",
            "Saint Lucia"           , "Saint Vincent and the Grenadines" , "Suriname",
            "Trinidad and Tobago"   , "United States"                    , "Uruguay",
            "US Virgin Islands"     , "Venezuela"
        });

        //Asia
        timezoneMap.put("Asia", new String[]{
            "Afghanistan"              , "Armenia"      , "Azerbaijan",
            "Bahrain"                  , "Bangladesh"   , "Bhutan",
            "Brunei Darussalam"        , "Cambodia"     , "China",
            "Cyprus"                   , "Georgia"      , "India",
            "Indonesia"                , "Iran"         , "Iraq",
            "Israel"                   , "Japan"        , "Jordan",
            "Kazakhstan"               , "Korea (North)", "Korea (South)",
            "Kuwait"                   , "Kyrgyzstan"   , "Laos",
            "Lebanon"                  , "Malaysia"     , "Maldives",
            "Mongolia"                 , "Myanmar"      , "Nepal",
            "Oman"                     , "Pakistan"     , "Philippines",
            "Qatar"                    , "Russia"       , "Saudi Arabia",
            "Singapore"                , "Sri Lanka"    , "Syria",
            "Taiwan"                   , "Tajikistan"   , "Thailand",
            "Timor-Leste (East Timor)" , "Turkey"       , "Turkmenistan"  ,
            "United Arab Emirates"     , "Uzbekistan"   , "Vietnam",
            "Yemen"
        });

        //Australia/Oceania
        timezoneMap.put("Australia/Oceania", new String[]{
            "American Samoa"    , "Australia"           , "Cook Islands",
            "Fiji"              , "French Polynesia"    , "Guam",
            "Hawaii"            , "Kiribati"            , "Marshall Islands",
            "Micronesia"        , "Nauru"               , "New Caledonia",
            "New Zealand"       , "Niue"                , "Northern Mariana Islands",
            "Palau"             , "Papua New Guinea"    , "Pitcairn",
            "Samoa"             , "Solomon Islands"     , "Tonga",
            "Tuvalu"            , "Vanuatu"
        });

        //Europe
        timezoneMap.put("Europe", new String[]{
            "Albania"           , "Andorra"     , "Austria",
            "Belarus"           , "Belgium"     , "Bosnia and Herzegovina",
            "Bulgaria"          , "Croatia"     , "Cyprus",
            "Czech Republic"    , "Denmark"     , "Estonia",
            "Faroe Islands"     , "Finland"     , "France",
            "Germany"           , "Gibraltar"   , "Greece",
            "Greenland"         , "Hungary"     , "Iceland",
            "Ireland"           , "Italy"       , "Latvia",
            "Liechtenstein"     , "Lithuania"   , "Luxembourg",
            "Macedonia"         , "Malta"       , "Moldova",
            "Monaco"            , "Montenegro"  , "Netherlands",
            "Norway"            , "Poland"      , "Portugal",
            "Romania"           , "San Marino"  , "Serbia",
            "Slovakia"          , "Slovenia"    , "Spain",
            "Sweden"            , "Switzerland" , "Ukraine",
            "United Kingdom"
        });
    }

    /**
     * IN SETTING THE REGIONS, A HELPER IS NOT USED FOR ITERATIONS DUE TO JAVA's INHERENT
     * PASS-BY-VALUE MECHANISM FOR METHOD PARAMETERS.
     * (i.e. A helper would two copies of the arrays in memory for small gains in code readability)
     */
    /**
     * Set the timezones for each country in the African region
     */
    private void setAfricanTimezonesInMap() {

        String[] africanCountriesWithOneTimezone = new String[]
        {
            "Algeria"           , "Angola"          , "Benin",
            "Botswana"          , "Burkina Faso"    , "Burundi",
            "Cameroon"          , "Cape Verde"      , "Central African Republic",
            "Chad"              , "Comoros"         , "Congo-Brazzaville",
            "Cote d\'Ivoire"    , "Djibouti"        , "Egypt",
            "Equatorial Guinea" , "Eritrea"         , "Ethiopia",
            "Gabon"             , "Gambia"          , "Ghana",
            "Guinea"            , "Guinea-Bissau"   , "Kenya",
            "Lesotho"           , "Liberia"         , "Libya",
            "Madagascar"        , "Malawi"          ,"Mali",
            "Mauritania"        , "Mauritius"       , "Morocco",
            "Mozambique"        , "Namibia"         , "Niger",
            "Nigeria"           , "Rwanda"          , "Senegal",
            "Sierra Leone"      , "Somalia"         , "South Africa",
            "South Sudan"       , "Sudan"           , "Swaziland",
            "Tanzania"          , "Togo"            ,  "Tunisia",
            "Uganda"            , "Zambia"          , "Zimbabwe"
        };

        String[] timezonesForAfricanCountriesWithOneTimezone = new String[]
        {
        "West Africa Time"           , "West Africa Time"           , "West Africa Time",
        "Central Africa Time"        , "Coordinated Universal Time" , "Central Africa Time",
        "West Africa Time"           , "Cape Verde Time"            , "West Africa Time",
        "West Africa Time"           , "East Africa Time"           , "West Africa Time",
        "Coordinated Universal Time" , "East Africa Time"           , "Egyptian Time(DST)",
        "West Africa Time"           , "East Africa Time"           , "East Africa Time",
        "West Africa Time"           , "Coordinated Universal Time" , "Coordinated Universal Time",
        "Coordinated Universal Time" , "Coordinated Universal Time" , "East Africa Time",
        "Central Africa Time"        , "Coordinated Universal Time" , "Libyan Time(DST)",
        "East Africa Time"           , "Central Africa Time"        , "Coordinated Universal Time",
        "Coordinated Universal Time" , "Mauritius Time"             , "Moroccan Time(DST)",
        "Central Africa Time"        , "West Africa Time(DST)"      , "West Africa Time",
        "West Africa Time"           , "Central Africa Time"        , "Coordinated Universal Time",
        "Coordinated Universal Time" , "East Africa Time"           , "Central Africa Time",
        "East Africa Time"           , "East Africa Time"           , "Central Africa Time",
        "East Africa Time"           , "Coordinated Universal Time" , "West Africa Time",
        "East Africa Time"           , "Central Africa Time"        , "Central Africa Time"
        };

        String[] timezoneArrayContainer = new String[1];
        for(int i=0; i<africanCountriesWithOneTimezone.length; i++) {
            timezoneArrayContainer[0] = timezonesForAfricanCountriesWithOneTimezone[i];
            timezoneMap.put(africanCountriesWithOneTimezone[i], timezoneArrayContainer);
        }

        //"Congo-Kinshasa" has two timezones, input do this separately
        timezoneMap.put("Congo-Kinshasa", new String[]
         {
            "West Africa Time", "Central Africa Time"
         });
    }


    private void setAmericasTimezonesInMap() {
        String[] americasCountriesWithOneTimezone = new String[]
        {
            "Anguilla"               , "Antigua And Barbuda" , "Argentina",
            "Aruba"                  , "Bahamas"             , "Barbados",
            "Belize"                 , "Bermuda"             , "Bolivia",
            "British Virgin Islands" , "CaymanIslands"       , "Colombia"
        };

        String[] timezonesForAmericasCountriesWithOneTimezone = new String[]
        {
            "Atlantic Standard Time" , "Atlantic Standard Time" , "Argentine Time",
            "Atlantic Standard Time" , "Eastern Time(DST)"      , "Atlantic Standard Time",
            "Central Standard Time"  , "Atlantic Time(DST)"     , "Atlantic Standard Time",
            "Atlantic Standard Time" , "Eastern Standard Time"  , "Colombia Time"
        };

        //Place the single timezone countries into the map
        String[] timezoneArrayContainer = new String[1];
        for(int i=0; i<americasCountriesWithOneTimezone.length; i++) {
            timezoneArrayContainer[0] = timezonesForAmericasCountriesWithOneTimezone[i];
            timezoneMap.put(americasCountriesWithOneTimezone[i], timezoneArrayContainer);
        }


        //Place the countries with more than one timezone into the map
        timezoneMap.put("Canada", new String[]
        {
            "Atlantic Time(DST)", "Central Time(DST)"     , "Eastern Time(DST)",
            "Mountain Time(DST)", "Newfoundland Time(DST)", "Pacific Time(DST)"
        });

        timezoneMap.put("Mexico", new String[]
        {
            "Central Time(DST)(m)" , "Mountain Standard Time",
            "Mountain Time(DST)(m)", "Pacific Time(DST)(m)"
        });

        timezoneMap.put("United States", new String[]
        {
            "Alaskan Time(DST)" , "Atlantic Time(DST)"       , "Central Time(DST)",
            "Eastern Time(DST)" , "Hawaii-Aleutian Time(DST)", "Mountain Standard Time",
            "Mountain Time(DST)", "Pacific Time(DST)"
        });

        timezoneMap.put("Brazil", new String[]
        {
            "Atlantic Standard Time", "Fernando de Noronha Time", "Brasilia Time(DST)"
        });

        timezoneMap.put("Chile", new String[]
        {
            "Chile Time(DST)", "Easter Island Time(DST)"
        });
    }

    private void setAsianTimezonesInMap() {
        String[] asianCountriesWithOneTimezone = new String[]
        {
            "Afghanistan"               , "Armenia"      , "Azerbaijan",
            "Bahrain"                   , "Bangladesh"   , "Bhutan",
            "Brunei"                    , "Cambodia"     , "China",
            "Cyprus"                    , "Georgia"      , "India",
            "Iran"                      , "Iraq"         , "Israel",
            "Japan"                     , "Jordan"       , "Kazakhstan",
            "Korea(North)"              , "Korea(South)" , "Kuwait",
            "Kyrgyzstan"                , "Laos"         , "Lebanon",
            "Malaysia"                  , "Maldives"     , "Myanmar",
            "Nepal"                     , "Oman"         , "Pakistan",
            "Philippines"               , "Qatar"        , "Saudi Arabia",
            "Singapore"                 , "Sri Lanka"    , "Syria",
            "Taiwan"                    , "Tajikistan"   , "Thailand",
            "Timor-Leste (East Timor)"  , "Turkey"       , "Turkmenistan",
            "United Arab Emirates"      , "Uzbekistan"   , "Vietnam",
            "Yemen"
        };

        String[] timezonesForAsianCountriesWithOneTimezone = new String[]
        {
            "Afghanistan Time"          , "Armenia Time"         , "Azerbaijan Time",
            "Arabia Standard Time"      , "Bangladesh Time"      , "Bhutan Time",
            "Brunei Time"               , "Indochina Time"       , "China Standard Time",
            "Eastern Europe Time(DST)"  , "Georgia Time"         , "India Standard Time",
            "Iran Time(DST)"            , "Arabia Standard Time" , "Israel Time",
            "Japan Standard Time"       , "Jordan Time"          , "Aqtobe Time",
            "North Korea Time"          , "South Korea Time"     , "Arabia Standard Time",
            "Kirgizstan Time"           , "Indochina Time"       , "Eastern Europe Time(DST)",
            "Malaysia Time"             , "Maldives Time"        , "Myanmar Time",
            "Nepal Time"                , "Gulf Standard Time"   , "Pakistan Time",
            "Philippines Time"          , "Arabia Standard Time" , "Arabia Standard Time",
            "Singapore  Time"           , "India Standard Time"  , "Syria Time",
            "China Standard Time"       , "Tajikistan Time"      , "Indochina Time",
            "Timor-Leste Time"          , "Turkey Time"          , "Turkmenistan Time",
            "Gulf Standard Time"        , "Uzbekistan Time"      , "Indochina Time",
            "Arabia Standard Time"
        };

        //Place the single timezone countries into the map
        String[] timezoneArrayContainer = new String[1];
        for(int i=0; i<asianCountriesWithOneTimezone.length; i++) {
            timezoneArrayContainer[0] = timezonesForAsianCountriesWithOneTimezone[i];
            timezoneMap.put(asianCountriesWithOneTimezone[i], timezoneArrayContainer);
        }


        //Put Asian countries with more than one timezone into the map
        timezoneMap.put("Indonesia", new String[]
        {
            "Central Indonesia Time", "West Indonesia Time", "East Indonesia Time"
        });

        timezoneMap.put("Mongolia", new String[]
        {
            "Hovd Time", "Ulaanbaatar Time", "Choibalsan Time"
        });

        timezoneMap.put("Russia", new String[]
        {
            "Further-eastern European Time" , "Moscow Time"                 , "Yekaterinburg Time",
            "Omsk Time"                     , "Krasnoyarsk Time"            ,  "Irkutsk" ,
            "Yakutsk Time"                  , "Vladivostok Standard Time"   , "Magadan Time"
        });
    }

    private void setAustralianOceaniaTimezoneInMap() {
        String[] australianOceaniaCountriesWithOneTimezone = new String[]
        {
            "American Samoa"   , "Cook Islands"     , "Fiji",
            "French Polynesia" , "Guam"             , "Hawaii",
            "Marshall Islands" , "Nauru"            , "New Caledonia",
            "New Zealand"      , "Niue"             , "Northern Mariana Islands",
            "Palau"            , "Papua New Guinea" , "Pitcairn",
            "Samoa"            , "Solomon Islands"  , "Tonga",
            "Tuvalu"           , "Vanuatu"
        };

        String[] timezonesForAustralianOceaniaCountriesWithOneTimezone = new String[]
        {
            "Samoa Standard Time"   , "Cook Island Time"       , "Fiji Time",
            "Tahiti Time"           , "Chamorro Standard Time" , "Hawaii Standard Time",
            "Marshall Islands Time" , "Nauru Time"             , "New Caledonia Time",
            "New Zealand Time"      , "Niue Time"              , "Chamorro Standard Time",
            "Palau Time"            , "Papua New Guinea Time"  , "Pitcairn Standard Time",
            "West Samoa Time"       , "Solomon Is. Time"       , "Tonga Time",
            "Tuvalu Time"           , "Vanuatu Time"
        };

        //Place the single timezone countries into the map
        String[] timezoneArrayContainer = new String[1];
        for(int i=0; i<australianOceaniaCountriesWithOneTimezone.length; i++) {
            timezoneArrayContainer[0] = timezonesForAustralianOceaniaCountriesWithOneTimezone[i];
            timezoneMap.put(australianOceaniaCountriesWithOneTimezone[i], timezoneArrayContainer);
        }


        //Put Australian/Oceanic countries with more than one timezone into the map
        timezoneMap.put("Australia", new String[]
        {
            "Central Standard Time(Aus)", "Central Time(Aus)", "Eastern Standard Time(Aus)",
            "Eastern Time(Aus)", "Western Standard Time(Aus)"
        });

        timezoneMap.put("Kiribati", new String[]
        {
            "Gilbert Island Time", "Phoenix Island Time", "Line Island Time"
        });

        timezoneMap.put("Micronesia", new String[]
        {
            "Yap Time", "Chuuk Time", "Kosrae Time", "Pohnpei Time"
        });

    }

    //TODO finish the timezone denominations
    private void setEuropeanTimezoneInMap() {
        String[] europeanCountriesWithOneTimezone = new String[]
        {
            "Albania"           , "Andorra"        , "Austria",
            "Belarus"           , "Belgium"        , "Bosnia and Herzegovina",
            "Bulgaria"          , "Croatia"        , "Cyprus",
            "Czech Republic"    , "Denmark"        , "Estonia",
            "Faroe Islands"     , "Finland"        , "France",
            "Germany"           , "Gibraltar"      , "Greece",
            "Hungary"           , "Iceland"        , "Ireland",
            "Italy"             , "Latvia"         , "Liechtenstein",
            "Lithuania"         , "Luxembourg"     , "Macedonia",
            "Malta"             , "Moldova"        , "Monaco",
            "Montenegro"        , "Netherlands"    , "Norway",
            "Poland"            , "Portugal"       , "Romania",
            "San Marino"        , "Serbia"         , "Slovakia",
            "Slovenia"          , "Spain"          , "Sweden",
            "Switzerland"       , "United Kingdom" ,
        };

        String[] timezonesForEuropeanCountriesWithOneTimezone = new String[]
        {
            "Central Europe Time" , "Central Europe Time" , "Central Europe Time",
            "Belarus Time"        , "Central Europe Time" , "Central Europe Time",
            "Eastern Europe Time" , "Central Europe Time" , "Eastern Europe Time",
            "Central Europe Time" , "Central Europe Time" , "Eastern Europe Time",
            "Western Europe Time" , "Eastern Europe Time" , "Central Europe Time",
            "Central Europe Time" , "Central Europe Time" , "Eastern Europe Time",
            "Central Europe Time" , "GMT"                 , "Ireland Time"       ,
            "Central Europe Time" , "Eastern Europe Time" , "Central Europe Time",
            "Eastern Europe Time" , "Central Europe Time" , "Central Europe Time",
            "Central Europe Time" , "Eastern Europe Time" , "Central Europe Time",
            "Central Europe Time" , "Central Europe Time" , "Central Europe Time",
            "Central Europe Time" , "Central Europe Time" , "Eastern Europe Time",
            "Central Europe Time" , "Central Europe Time" , "Central Europe Time",
            "Central Europe Time" , "Central Europe Time" , "Central Europe Time",
            "Central Europe Time" , "UK Time"
        };

        //Place the single timezone countries into the map
        String[] timezoneArrayContainer = new String[1];
        for(int i=0; i<europeanCountriesWithOneTimezone.length; i++) {
            timezoneArrayContainer[0] = timezonesForEuropeanCountriesWithOneTimezone[i];
            timezoneMap.put(europeanCountriesWithOneTimezone[i], timezoneArrayContainer);
        }


        //Put European countries with more than one timezone into the map
        timezoneMap.put("Greenland", new String[]
        {
            "Atlantic Time(DST)", "West Greenland Time", "East Greenland Time"
        });

        timezoneMap.put("Ukraine", new String[]
        {
            "Eastern Europe Time", "Moscow Time"
        });
    }

    /*
    public static void main(String[] args) {
        //String[] tz = java.util.TimeZone.getAvailableIDs();
        java.util.TimeZone t = java.util.TimeZone.getTimeZone("EET");

        for( String s : tz ) {
            System.out.println(s);
        }

        System.out.println(t.inDaylightTime(new java.util.Date()));

        System.out.println(t.inDaylightTime(new java.util.Date(2014, 10, 10)));
    }
    */
}
