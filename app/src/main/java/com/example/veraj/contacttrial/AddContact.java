package com.example.veraj.contacttrial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

public class AddContact extends AppCompatActivity {

    public static final String CONTACT_CLASS = "Contact Class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        endActivityButton();
    }

    public void endActivityButton (){


        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText firstName = (EditText) findViewById(R.id.firstName);
                EditText lastName = (EditText) findViewById(R.id.lastName);
                final Contact contact = new Contact();

                //Extract Data from Add Contact UI
                contact.setFirstName(firstName.getText().toString());
                contact.setLastName(lastName.getText().toString());

                //Pass Data abck
                Intent intent = new Intent();
                intent.putExtra(CONTACT_CLASS, contact);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });
    }

    public static Intent makeIntent (Context context){
        Intent intent = new Intent(context, AddContact.class);
        return intent;
    }
    public static Serializable getObject (Intent intent){
        return intent.getSerializableExtra(CONTACT_CLASS);
    }
}
