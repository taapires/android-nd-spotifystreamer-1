package com.taapires.android_nd_spotifystreamer_1.UI;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taapires.android_nd_spotifystreamer_1.Adapters.ArtistsAdapter;
import com.taapires.android_nd_spotifystreamer_1.Models.ArtistParcelable;
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

    private final String BUNDLE_ARTISTS = "BUNDLE_ARTISTS";
    private final String BUNDLE_SEARCH_QUERY = "BUNDLE_SEARCH_QUERY";

    // construct the data source
    private ArrayList<ArtistParcelable> mArtists = new ArrayList<>();
    private ArtistsAdapter mAdapter;

    private String mSearchQuery;
    private EditText mSearchEditText;

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView_artists); // get the listview
        mSearchEditText = (EditText) rootView.findViewById(R.id.editText_searchArtist);

        // save the artists and search query to the instance state
        if (savedInstanceState != null) {
            mArtists = savedInstanceState.getParcelableArrayList(BUNDLE_ARTISTS);
            mSearchQuery = savedInstanceState.getString(BUNDLE_SEARCH_QUERY);
        } else {
            mArtists = new ArrayList<>();
        }

        // create the adapter to convert the array to views
        mAdapter = new ArtistsAdapter(getActivity(), mArtists);
        // attach the adapter to a listview
        listView.setAdapter(mAdapter); // populate the listview with the adapter

        // search artist
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchArtist(v.getText());
                    v.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                    Toast.makeText(getActivity(), "Searching for " + v.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        // listview click item handler
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArtistParcelable artist = mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), TopTracksActivity.class).putExtra("ARTIST", artist);
                startActivity(intent);
            }
        });

        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the artists and the search query
        outState.putParcelableArrayList(BUNDLE_ARTISTS, mArtists);
        outState.putString(BUNDLE_SEARCH_QUERY, mSearchQuery);
    }

    private void searchArtist(CharSequence query) {

        // hide keyboard on search
        mSearchEditText.clearFocus();
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);

        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();

        spotify.searchArtists(query.toString(), new Callback<ArtistsPager>() {
            @Override
            public void success(final ArtistsPager artistsPager, Response response) {

                // clear the artists array on search
                mArtists.clear();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (artistsPager.artists.items.isEmpty()) {
                            Toast.makeText(getActivity(), "Artist not found", Toast.LENGTH_SHORT).show();
                        } else {
                            for (Artist artist : artistsPager.artists.items) {
                                mAdapter.add(new ArtistParcelable(artist));
                            }
                        }
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Artists failure", error.toString());
            }
        });
    }
}
