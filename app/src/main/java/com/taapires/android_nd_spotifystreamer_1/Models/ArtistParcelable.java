package com.taapires.android_nd_spotifystreamer_1.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by telmo on 23/06/15.
 */
public class ArtistParcelable implements Parcelable {
    public String artistName;
    public String artistImage;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.artistName);
        dest.writeString(this.artistImage);
    }

    public ArtistParcelable(String artistName, String artistImage) {
        this.artistName = artistName;
        this.artistImage = artistImage;
    }

    protected ArtistParcelable(Parcel in) {
        this.artistName = in.readString();
        this.artistImage = in.readString();
    }

    public static final Parcelable.Creator<ArtistParcelable> CREATOR = new Parcelable.Creator<ArtistParcelable>() {
        public ArtistParcelable createFromParcel(Parcel source) {
            return new ArtistParcelable(source);
        }

        public ArtistParcelable[] newArray(int size) {
            return new ArtistParcelable[size];
        }
    };
}
