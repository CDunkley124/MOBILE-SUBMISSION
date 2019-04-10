//
// Name                 Callum Dunkley
// Student ID           S1510033
// Programme of Study   Computing
//

package com.example.cdunkl200.mobile_coursework;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Earthquake implements Parcelable {

    private double magnitude;
    private Date quakeDate;
    private String location;
    private double latitude;
    private double longitude;
    private double depth;

    public Earthquake(double magnitude, Date quakeDate, String location, double lat, double longitude, double depth) {
        this.magnitude = magnitude;
        this.quakeDate = quakeDate;
        this.location = location;
        this.latitude = lat;
        this.longitude = longitude;
        this.depth = depth;
    }

    public Earthquake() {
    }

    protected Earthquake(Parcel in) {
        magnitude = in.readDouble();
        location = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        depth = in.readDouble();
    }

    public static final Creator<Earthquake> CREATOR = new Creator<Earthquake>() {
        @Override
        public Earthquake createFromParcel(Parcel in) {
            return new Earthquake(in);
        }

        @Override
        public Earthquake[] newArray(int size) {
            return new Earthquake[size];
        }
    };

    public Date getQuakeDate() {
        return quakeDate;
    }

    public void setQuakeDate(Date quakeDate) {
        this.quakeDate = quakeDate;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public double getLat() {
        return latitude;
    }

    public void setLat(double latitude) {
        this.latitude = latitude;
    }


    public double getLong() {
        return longitude;
    }

    public void setLong(double longitude) { this.longitude = longitude; }


    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }


    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }


    @Override
    public String toString() {
        return "Earthquake{" +
                "magnitude=" + magnitude +
                ", quakeDate=" + quakeDate +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", depth=" + depth +
                '}';
    }

    public String baseQuakeToString(){
        return "Location: " + location +
                "\nMagnitude: " + magnitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(magnitude);
        dest.writeString(location);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(depth);
    }
}