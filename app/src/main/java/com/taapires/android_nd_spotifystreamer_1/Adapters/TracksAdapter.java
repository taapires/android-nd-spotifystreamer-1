package com.taapires.android_nd_spotifystreamer_1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.taapires.android_nd_spotifystreamer_1.Models.TrackParcelable;
import com.taapires.android_nd_spotifystreamer_1.R;

import java.util.ArrayList;

/**
 * Created by telmo on 23/06/15.
 */
public class TracksAdapter extends ArrayAdapter<TrackParcelable> {
    // View lookup cache
    private static class ViewHolder {
        TextView trackName;
        TextView albumName;
        ImageView albumImage;
    }

    public TracksAdapter(Context context, ArrayList<TrackParcelable> tracks) {
        super(context, R.layout.list_item_toptrack, tracks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get the data item for this position
        TrackParcelable track = getItem(position);

        // check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_toptrack, parent, false);
            viewHolder.trackName = (TextView) convertView.findViewById(R.id.textView_topTrack_trackName);
            viewHolder.albumName = (TextView) convertView.findViewById(R.id.textView_topTrack_albumName);
            viewHolder.albumImage = (ImageView) convertView.findViewById(R.id.imageView_topTrack_albumImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // populate the data into the template view using the data object
        viewHolder.trackName.setText(track.trackName);
        viewHolder.albumName.setText(track.albumName);
        Picasso.with(getContext()).load(track.albumImage).into(viewHolder.albumImage);

        //return super.getView(position, convertView, parent);
        return convertView;
    }
}
