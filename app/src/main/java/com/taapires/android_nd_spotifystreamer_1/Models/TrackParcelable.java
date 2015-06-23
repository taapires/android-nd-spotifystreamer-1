package com.taapires.android_nd_spotifystreamer_1.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by telmo on 23/06/15.
 */
public class TrackParcelable implements Parcelable {
    public String trackName;
    public String albumName;
    public String albumImage;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.trackName);
        dest.writeString(this.albumName);
        dest.writeString(this.albumImage);
    }

    public TrackParcelable(String trackName, String albumName, String albumImage) {
        this.trackName = trackName;
        this.albumName = albumName;
        this.albumImage = albumImage;
    }

    protected TrackParcelable(Parcel in) {
        this.trackName = in.readString();
        this.albumName = in.readString();
        this.albumImage = in.readString();
    }

    public static final Parcelable.Creator<TrackParcelable> CREATOR = new Parcelable.Creator<TrackParcelable>() {
        public TrackParcelable createFromParcel(Parcel source) {
            return new TrackParcelable(source);
        }

        public TrackParcelable[] newArray(int size) {
            return new TrackParcelable[size];
        }
    };
}
