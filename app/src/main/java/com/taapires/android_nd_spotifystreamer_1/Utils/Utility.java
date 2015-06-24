package com.taapires.android_nd_spotifystreamer_1.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.taapires.android_nd_spotifystreamer_1.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by telmo on 24/06/15.
 */
public class Utility {

    public static String getPreferredLocation(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_location_key),
                context.getString(R.string.pref_location_default));
    }

    public static List<String> getCountryCodes(Context context) {
        /*String[] countryCodes = {
                "AD", "AR", "AT", "AU", "BE", "BG", "BO", "BR", "CA", "CH", "CL", "CO", "CR", "CY", "CZ", "DE", "DK", "DO", "EC", "EE", "ES", "FI", "FR", "GB", "GR", "GT", "HK", "HN", "HU", "IE", "IS", "IT", "LI", "LT", "LU", "LV", "MC", "MT", "MX", "MY", "NI", "NL", "NO", "NZ", "PA", "PE", "PH", "PL", "PT", "PY", "RO", "SE", "SG", "SI", "SK", "SV", "TR", "TW", "US", "UY"
        };*/

        // get country codes from the resources
        String[] countryCodes = context.getResources().getStringArray(R.array.country_codes);
        return Arrays.asList(countryCodes);
    }
}
