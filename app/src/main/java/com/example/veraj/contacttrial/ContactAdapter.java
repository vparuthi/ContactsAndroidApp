package com.example.veraj.contacttrial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Veraj on 2018-03-21.
 */

class ContactAdapter extends ArrayAdapter <Contact>{

    public ContactAdapter(@NonNull Context context, ArrayList<Contact> contacts) {
        super(context, R.layout.custon_row,contacts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.custon_row, parent, false);

        Contact contact = getItem(position);
        TextView first_name = (TextView) view.findViewById(R.id.firstName);
        TextView last_name = (TextView) view.findViewById(R.id.lastName);
        ImageView addPhoto = (ImageView) view.findViewById(R.id.addPhoto);

        first_name.setText(contact.getFirstName());
        last_name.setText(contact.getLastName());
        addPhoto.setImageBitmap(BitmapUtil.getThumbnail("contact" + contact.getFirstName() +".png", view.getContext()));
        return view;
    }
}

