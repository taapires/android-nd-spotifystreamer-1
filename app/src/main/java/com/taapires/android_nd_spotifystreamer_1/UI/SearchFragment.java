package com.taapires.android_nd_spotifystreamer_1.UI;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taapires.android_nd_spotifystreamer_1.Models.ArtistParcelable;
import com.taapires.android_nd_spotifystreamer_1.Adapters.ArtistsAdapter;
import com.taapires.android_nd_spotifystreamer_1.R;
import com.taapires.android_nd_spotifystreamer_1.Utils.Utility;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    private final String BUNDLE_ARTISTS = "BUNDLE_ARTISTS";
    private final String BUNDLE_SEARCH_QUERY = "BUNDLE_SEARCH_QUERY";

    // construct the data source
    private ArrayList<ArtistParcelable> artists = new ArrayList<>();
    private ArtistsAdapter adapter;

    private String searchQuery;

    public SearchFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView_artists); // get the listview
        final EditText search = (EditText) rootView.findViewById(R.id.editText_searchArtist);
        //Button btn = (Button) rootView.findViewById(R.id.btn);

        // save the artists and search query to the instance state
        if (savedInstanceState != null) {
            artists = savedInstanceState.getParcelableArrayList(BUNDLE_ARTISTS);
            searchQuery = savedInstanceState.getString(BUNDLE_SEARCH_QUERY);
        } else {
            artists = new ArrayList<>();
        }

        /*String[] myStrings = {
                "Telmo",
                "Tete"
        };

        List<String> artists = new ArrayList<>(Arrays.asList(myStrings));
*/
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_artist, // the name of the layout ID
                R.id.textView_artist_name, // the id of the textview to populate
                artists);*/

        // create the adapter to convert the array to views
        adapter = new ArtistsAdapter(getActivity(), artists);
        // attach the adapter to a listview
        listView.setAdapter(adapter); // populate the listview with the adapter

        // populate the listview
        /*ArtistParcelable newArtist = new ArtistParcelable("Telmo", "http://i.imgur.com/DvpvklR.png");
        ArtistParcelable newArtist2 = new ArtistParcelable("Pires", "http://i.imgur.com/DvpvklR.png");
        adapter.add(newArtist);
        adapter.add(newArtist2);*/

        // search for an artist com enter text
        /*search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchArtist(search.getText());
            }
        });*/

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getAction() == KeyEvent.ACTION_DOWN) return true;
                searchArtist(v.getText());
                return true;
            }
        });

        // listview click item handler
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArtistParcelable artist = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), TopTracks.class).putExtra("ARTIST", artist);
                startActivity(intent);
            }
        });
/*
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopTracks.class);
                startActivity(intent);
            }
        });*/

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the artists and the search query
        outState.putParcelableArrayList(BUNDLE_ARTISTS, artists);
        outState.putString(BUNDLE_SEARCH_QUERY, searchQuery);
    }

    private void searchArtist(CharSequence query) {
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();

        spotify.searchArtists(query.toString(), new Callback<ArtistsPager>() {
            @Override
            public void success(final ArtistsPager artistsPager, Response response) {

                // clear the artists array on search
                artists.clear();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (artistsPager.artists.items.isEmpty()) {
                            Toast.makeText(getActivity(), "Artist not found", Toast.LENGTH_SHORT).show();
                        } else {
                            for (Artist artist : artistsPager.artists.items) {
                                adapter.add(new ArtistParcelable(artist));
                            }
                        }
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
