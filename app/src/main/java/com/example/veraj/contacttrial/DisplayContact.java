package com.example.veraj.contacttrial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DisplayContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
    }
    public static Intent makeIntent (Context context){
        Intent intent = new Intent(context, Display.class);
        return intent;
    }

}
