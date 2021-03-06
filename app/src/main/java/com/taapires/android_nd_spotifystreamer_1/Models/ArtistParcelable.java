package com.taapires.android_nd_spotifystreamer_1.Models;

import android.os.Parcel;
import android.os.Parcelable;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by telmo on 23/06/15.
 */
public class ArtistParcelable implements Parcelable {
    public final String id;
    public final String artistName;
    public String artistImage;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.artistName);
        dest.writeString(this.artistImage);
    }

    public ArtistParcelable(Artist artist) {
        this.id = artist.id;
        this.artistName = artist.name;
        if (!artist.images.isEmpty()) artistImage = artist.images.get(0).url;
    }

    private ArtistParcelable(Parcel in) {
        this.id = in.readString();
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
