package com.taapires.android_nd_spotifystreamer_1.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.taapires.android_nd_spotifystreamer_1.R;

/**
 * Created by telmo on 24/06/15.
 */
public class Utility {

    public static String getPreferredLocation(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_location_key),
                context.getString(R.string.pref_location_default));
    }
}
