package com.softlica.bishal.triplescale.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by bishal on 7/8/2017.
 */

public class User implements Serializable {
    private String gender;
    private String dob;
    private Name name;
    private String fullName;
    private String fullAddress;
    private Location location;
    private Picture picture;
    private String distance;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "User{" +
                "gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", name=" + name +
                ", fullName='" + fullName + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", location=" + location +
                ", picture=" + picture +
                ", distance=" + distance +
                '}';
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }


    public class Name implements Serializable {
        @Override
        public String toString() {
            return "Name{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }

        @SerializedName("first")
        private String firstName;
        @SerializedName("last")
        private String lastName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    public class Location implements Serializable {
        @Override
        public String toString() {
            return "Location{" +
                    "city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    ", street='" + street + '\'' +
                    ", postcode='" + postcode + '\'' +
                    '}';
        }

        private String city;
        private String state;
        private String street;
        private String postcode;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }
    }

    public class Picture implements Serializable {
        @Override
        public String toString() {
            return "Picture{" +
                    "thumbnai='" + thumbnail + '\'' +
                    ", large='" + large + '\'' +
                    '}';
        }

        private String thumbnail;
        private String large;

        public String getThumbnai() {
            return thumbnail;
        }

        public void setThumbnai(String thumbnai) {
            this.thumbnail = thumbnai;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }
}
