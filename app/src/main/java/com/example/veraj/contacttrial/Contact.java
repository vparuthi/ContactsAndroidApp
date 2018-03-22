package com.example.veraj.contacttrial;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Veraj on 2018-03-15.
 */

public class Contact implements Serializable{
    private String firstName;
    private String lastName;
    private Bitmap contactPhoto;

    public Bitmap getContactPhoto() {
        return contactPhoto;
    }

    public void setContactPhoto(Bitmap contactPhoto) {
        this.contactPhoto = contactPhoto;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}

