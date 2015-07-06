package com.taapires.android_nd_spotifystreamer_1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taapires.android_nd_spotifystreamer_1.Models.ArtistParcelable;
import com.taapires.android_nd_spotifystreamer_1.R;

import java.util.ArrayList;

/**
 * Created by telmo on 23/06/15.
 */
public class ArtistsAdapter extends ArrayAdapter<ArtistParcelable> {

    // View lookup cache
    private static class ViewHolder {
        TextView artistName;
        ImageView artistImage;
    }

    public ArtistsAdapter(Context context, ArrayList<ArtistParcelable> artists) {
        super(context, R.layout.list_item_artist, artists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get the data item for this position
        ArtistParcelable artist = getItem(position);

        // check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_artist, parent, false);
            viewHolder.artistName = (TextView) convertView.findViewById(R.id.textView_artist_name);
            viewHolder.artistImage = (ImageView) convertView.findViewById(R.id.imageView_artist);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // populate the data into the template view using the data object
        viewHolder.artistName.setText(artist.artistName);
        if (artist.artistImage != null) {
            Picasso.with(getContext()).load(artist.artistImage).into(viewHolder.artistImage);
        } else {
            Picasso.with(getContext()).load(R.mipmap.image_na).into(viewHolder.artistImage);
        }



        //return super.getView(position, convertView, parent);
        return convertView;
    }
}
