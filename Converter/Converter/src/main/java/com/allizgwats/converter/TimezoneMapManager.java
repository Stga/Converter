package com.allizgwats.converter;

import java.util.HashMap;

class TimezoneMapManager {

    private final HashMap<String, String[]> timezoneMap = new HashMap<String, String[]>();

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
        setTimezoneReadableAndIdsInMap();
    }

    public HashMap<String, String[]> getTimezoneMap() {
        return timezoneMap;
    }

    /**
     * Put the World regions String array into the map
     */
    private void setRegionsInMap() {
        //Default level
        timezoneMap.put("Regions", new String[]{
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
            "Brunei"                   , "Cambodia"     , "China",
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

        //Place the single timezone countries into the map
        //TimezoneArrayContainer is delcared inside the loop as declaring outside and reassigning
        //each time causes all Keys to return the final Value of the value set. May have to do with
        //difference in heap/stack references when using HashMap.put()
        for(int i=0; i<africanCountriesWithOneTimezone.length; i++) {
            String[] timezoneArrayContainer = new String[1];
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
        //TimezoneArrayContainer is delcared inside the loop as declaring outside and reassigning
        //each time causes all Keys to return the final Value of the value set. May have to do with
        //difference in heap/stack references when using HashMap.put()
        for(int i=0; i<americasCountriesWithOneTimezone.length; i++) {
            String[] timezoneArrayContainer = new String[1];
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

        //Place the single timezone countries into the map.
        //TimezoneArrayContainer is delcared inside the loop as declaring outside and reassigning
        //each time causes all Keys to return the final Value of the value set. May have to do with
        //difference in heap/stack references when using HashMap.put().
        for(int i=0; i<asianCountriesWithOneTimezone.length; i++) {
            String[] timezoneArrayContainer = new String[1];
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
        //TimezoneArrayContainer is delcared inside the loop as declaring outside and reassigning
        //each time causes all Keys to return the final Value of the value set. May have to do with
        //difference in heap/stack references when using HashMap.put()
        for(int i=0; i<australianOceaniaCountriesWithOneTimezone.length; i++) {
            String[] timezoneArrayContainer = new String[1];
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
            "Switzerland"       , "United Kingdom"
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
        //TimezoneArrayContainer is delcared inside the loop as declaring outside and reassigning
        //each time causes all Keys to return the final Value of the value set. May have to do with
        //difference in heap/stack references when using HashMap.put()
        for(int i=0; i<europeanCountriesWithOneTimezone.length; i++) {
            String[] timezoneArrayContainer = new String[1];
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

    private void setTimezoneReadableAndIdsInMap() {
        String[] timezonesAsReadableArray = new String[]
        {
            "Afghanistan Time"         , "Alaskan Time(DST)"          , "Amazon Time",
            "Aqtobe Time"              , "Arabia Standard Time"       , "Argentine Time",
            "Armenia Time"             , "Atlantic Standard Time"     , "Atlantic Time(DST)",
            "Azerbaijan Time"          , "Bangladesh Time"            , "Belarus Time",
            "Bhutan Time"              , "Brasilia Time(DST)"         , "Brunei Time",
            "Cape Verde Time"          , "Central Africa Time"        , "Central Europe Time",
            "Central Indonesia Time"   , "Central Standard Time"      , "Central Standard Time(Aus)",
            "Central Time(Aus)"        , "Central Time(DST)"          , "Central Time(DST)(m)",
            "Chamorro Standard Time"   , "Chile Time(DST)"            , "China Standard Time",
            "Choibalsan Time"          , "Chuuk Time"                 , "Colombia Time",
            "Cook Island Time"         , "Coordinated Uni Time"       , "East Africa Time",
            "East Greenland Time"      , "East Indonesia Time"        , "Easter Island Time(DST)",
            "Eastern Europe Time"      , "Eastern Standard Time"      , "Eastern Standard Time(Aus)",
            "Eastern Time(Aus)"        , "Eastern Time(DST)"          , "Egyptian Time(DST)",
            "Fernando de Noronha Time" , "Fiji Time"                  , "Further-eastern European Time",
            "Georgia Time"             , "Gilbert Island Time"        , "GMT",
            "Gulf Standard Time"       , "Hawaii Standard Time"       , "Hawaii-Aleutian Time(DST)",
            "Hovd Time"                , "India Standard Time"        , "Indochina Time",
            "Iran Time(DST)"           , "Ireland Time"               , "Irkutsk",
            "Israel Time"              , "Japan Standard Time"        , "Jordan Time",
            "Kirgizstan Time"          , "Kosrae Time"                , "Krasnoyarsk Time",
            "Libya(DST)"               , "Line Island Time"           , "Magadan Time",
            "Malaysia Time"            , "Maldives Time"              , "Marshall Islands Time",
            "Moroccan Time(DST)"       , "Moscow Time"                , "Mountain Standard Time",
            "Mountain Time(DST)"       , "Mountain Time(DST)(m)"      , "Myanmar Time",
            "Nauru Time"               , "Nepal Time"                 , "New Caledonia Time",
            "New Zealand Time"         , "Newfoundland Time(DST)"     , "Niue Time",
            "North Korea Time"         , "Omsk Time"                  , "Pacific Time(DST)",
            "Pacific Time(DST)(m)"     , "Pakistan Time"              , "Palau Time",
            "Papua New Guinea Time"    , "Philippines Time"           , "Phoenix Island Time",
            "Pitcairn Standard Time"   , "Pohnpei Time"               , "Samoa Standard Time",
            "Singapore Time"           , "Solomon Is. Time"           , "South Korea Time",
            "Syria Time(DST)"          , "Tahiti Time"                , "Tajikistan Time",
            "Timor-Leste Time"         , "Tonga Time"                 , "Turkey Time",
            "Turkmenistan Time"        , "Tuvalu Time"                , "UK Time",
            "Ulaanbaatar Time"         , "Uzbekistan Time"            , "Vanuatu Time",
            "Vladivostok Standard Time", "West Africa Time"           , "West African Time(DST)",
            "West Greenland Time"      , "West Indonesia Time"        , "West Samoa Time",
            "Western Europe Time"      , "Western Standard Time(Aus)" , "Yakutsk Time",
            "Yap Time"                 , "Yekaterinburg Time"
        };

        String[] timezonesAsIdsArray = new String[]
        {
            "Asia/Kabul"            , "AST"                 , "Brazil/Acre",
            "Asia/Aqtobe"           , "Asia/Qatar"          , "AGT",
            "NET"                   , "PRT"                 , "Canada/Atlantic",
            "Asia/Baku"             , "Asia/Dacca"          , "Europe/Minsk",
            "Asia/Thimbu"           , "Brazil/East"         , "Asia/Brunei",
            "Atlantic/Cape_Verde"   , "Africa/Blantyre"     , "CET",
            "Asia/Makassar"         , "ACT"                 , "Australia/Darwin",
            "Australia/South"       , "Canada/Central"      , "Mexico/General",
            "Pacific/Guam"          , "America/Santiago"    , "CTT",
            "Asia/Choibalsan"       , "Pacific/Chuuk"       , "America/Bogota",
            "Pacific/Rarotonga"     , "Etc/UTC"             , "EAT",
            "America/Scoresbysund"  , "Asia/Jayapura"       , "Chile/EasterIsland",
            "EET"                   , "EST"                 , "Australia/Queensland",
            "Australia/Sydney"      , "Canada/Eastern"      , "Egypt",
            "Brazil/DeNoronha"      , "Pacific/Fiji"        , "Europe/Kaliningrad",
            "Asia/Tbilisi"          , "Pacific/Tarawa"      , "GMT",
            "Asia/Dubai"            , "HST"                 , "US/Aleutian",
            "Asia/Hovd"             , "IST"                 , "VST",
            "Iran"                  , "Eire"                , "Asia/Irkutsk",
            "Asia/Tel_Aviv"         , "Japan"               , "Asia/Amman",
            "Asia/Bishkek"          , "Pacific/Kosrae"      , "Asia/Krasnoyarsk",
            "Libya"                 , "Pacific/Kiritimati"  , "Asia/Magadan",
            "Asia/Kuala_Lumpur"     , "Indian/Maldives"     , "Kwajalein",
            "Africa/Casablanca"     , "Europe/Moscow"       , "MST",
            "Canada/Mountain"       , "Mexico/BajaSur"      , "Asia/Rangoon",
            "Pacific/Nauru"         , "Asia/Kathmandu"      , "Pacific/Noumea",
            "NST"                   , "Canada/Newfoundland" , "Pacific/Niue",
            "Asia/Pyongyang"        , "Asia/Omsk"           , "Canada/Pacific",
            "Mexico/BajaNorte"      , "Asia/Karachi"        , "Pacific/Palau",
            "Pacific/Port_Moresby"  , "Asia/Manila"         , "Pacific/Enderbury",
            "Pacific/Pitcairn"      , "Pacific/Pohnpei"     , "US/Samoa",
            "Singapore"             , "Pacific/Guadalcanal" , "Asia/Seoul",
            "Asia/Damascus"         , "Pacific/Tahiti"      , "Asia/Dushanbe",
            "Asia/Dili"             , "Pacific/Tongatapu"   , "Turkey",
            "Asia/Ashgabat"         , "Pacific/Funafuti"    , "GB",
            "Asia/Ulaanbaatar"      , "Asia/Samarkand"      , "Pacific/Efate",
            "Asia/Vladivostok"      , "Africa/Bangui"       , "Africa/Windhoek",
            "America/Godthab"       , "Asia/Pontianak"      , "MIT",
            "WET"                   , "Australia/West"      , "Asia/Khandyga",
            "Pacific/Yap"           , "Asia/Yekaterinburg"
        };

        //Place the single timezone countries into the map
        //TimezoneArrayContainer is delcared inside the loop as declaring outside and reassigning
        //each time causes all Keys to return the final Value of the value set. May have to do with
        //difference in heap/stack references when using HashMap.put()
        for(int i=0; i<timezonesAsReadableArray.length; i++) {
            String[] timezoneArrayContainer = new String[1];
            timezoneArrayContainer[0] = timezonesAsIdsArray[i];
            timezoneMap.put(timezonesAsReadableArray[i], timezoneArrayContainer);
        }
    }
}
