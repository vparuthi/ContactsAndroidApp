package com.example.veraj.contacttrial;

import java.io.Serializable;

/**
 * Created by Veraj on 2018-03-15.
 */

public class Contact implements Serializable{
    private String firstName;
    private String lastName;

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

