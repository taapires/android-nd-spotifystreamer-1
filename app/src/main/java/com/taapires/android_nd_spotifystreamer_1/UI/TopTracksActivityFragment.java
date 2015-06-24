package com.taapires.android_nd_spotifystreamer_1.UI;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.taapires.android_nd_spotifystreamer_1.Models.ArtistParcelable;
import com.taapires.android_nd_spotifystreamer_1.R;
import com.taapires.android_nd_spotifystreamer_1.Models.TrackParcelable;
import com.taapires.android_nd_spotifystreamer_1.Adapters.TracksAdapter;
import com.taapires.android_nd_spotifystreamer_1.Utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTracksActivityFragment extends Fragment {

    private TracksAdapter mAdapter;

    public TopTracksActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top_tracks, container, false);

        // construct the data source
        ArrayList<TrackParcelable> tracks = new ArrayList<>();
        // create the adapter to convert the array to views
        mAdapter = new TracksAdapter(getActivity(), tracks);
        // attach the adapter to a listview
        ListView listView = (ListView) rootView.findViewById(R.id.listView_toptracks); // get the listview
        listView.setAdapter(mAdapter); // populate the listview with the adapter

        // populate the listview
       /* TrackParcelable newTrack = new TrackParcelable("Telmo", "Pires", "http://i.imgur.com/DvpvklR.png");
        TrackParcelable newTrack2 = new TrackParcelable("Test", "André", "http://i.imgur.com/DvpvklR.png");
        adapter.add(newTrack);
        adapter.add(newTrack2);*/

        getTopTracks();

        return rootView;
    }

    // get the artist from the search activity
    private ArtistParcelable getArtist() {
        return getActivity().getIntent().getParcelableExtra("ARTIST");
    }

    private void getTopTracks() {
        Map<String, Object> params = new HashMap<>();
        params.put("country", Utility.getPreferredLocation(getActivity()));

        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();

        spotify.getArtistTopTrack(getArtist().id, params, new Callback<Tracks>() {
            @Override
            public void success(final Tracks tracks, Response response) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (tracks.tracks.isEmpty()) {
                            Toast.makeText(getActivity(), "Tracks not found", Toast.LENGTH_SHORT).show();
                        } else {
                            for (Track track : tracks.tracks) {
                                mAdapter.add(new TrackParcelable(track));
                            }
                        }
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Top tracks failure", error.toString());
            }
        });
    }
}
