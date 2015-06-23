package com.taapires.android_nd_spotifystreamer_1.UI;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.taapires.android_nd_spotifystreamer_1.R;
import com.taapires.android_nd_spotifystreamer_1.Models.TrackParcelable;
import com.taapires.android_nd_spotifystreamer_1.Adapters.TracksAdapter;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTracksFragment extends Fragment {

    public TopTracksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false);

        // construct the data source
        ArrayList<TrackParcelable> tracks = new ArrayList<>();
        // create the adapter to convert the array to views
        TracksAdapter adapter = new TracksAdapter(getActivity(), tracks);
        // attach the adapter to a listview
        ListView listView = (ListView) rootView.findViewById(R.id.listView_toptracks); // get the listview
        listView.setAdapter(adapter); // populate the listview with the adapter

        // populate the listview
        TrackParcelable newTrack = new TrackParcelable("Telmo", "Pires", "http://i.imgur.com/DvpvklR.png");
        TrackParcelable newTrack2 = new TrackParcelable("Test", "André", "http://i.imgur.com/DvpvklR.png");
        adapter.add(newTrack);
        adapter.add(newTrack2);

        return rootView;
    }
}
