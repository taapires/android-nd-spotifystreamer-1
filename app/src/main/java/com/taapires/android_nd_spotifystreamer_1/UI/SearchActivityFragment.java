package com.taapires.android_nd_spotifystreamer_1.UI;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.taapires.android_nd_spotifystreamer_1.Models.ArtistParcelable;
import com.taapires.android_nd_spotifystreamer_1.Adapters.ArtistsAdapter;
import com.taapires.android_nd_spotifystreamer_1.R;

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
public class SearchActivityFragment extends Fragment {

    // construct the data source
    private ArrayList<ArtistParcelable> artists = new ArrayList<>();
    private ArtistsAdapter adapter;

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        final EditText search = (EditText) rootView.findViewById(R.id.editText_searchArtist);
        Button btn = (Button) rootView.findViewById(R.id.btn);

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
        ListView listView = (ListView) rootView.findViewById(R.id.listView_artists); // get the listview
        listView.setAdapter(adapter); // populate the listview with the adapter

        // populate the listview
        /*ArtistParcelable newArtist = new ArtistParcelable("Telmo", "http://i.imgur.com/DvpvklR.png");
        ArtistParcelable newArtist2 = new ArtistParcelable("Pires", "http://i.imgur.com/DvpvklR.png");
        adapter.add(newArtist);
        adapter.add(newArtist2);*/

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchArtist(search.getText());
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopTracks.class);
                startActivity(intent);
            }
        });

        return rootView;
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
                        for (Artist artist : artistsPager.artists.items) {
                            adapter.add(new ArtistParcelable(artist));
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
