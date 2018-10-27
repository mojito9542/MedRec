package com.illuminati.www.medrec;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileDetails extends Activity {
    private EditText name,address1,address2,contact;
    private Button saveButton,editButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        name=findViewById(R.id.enterName);
        address1=findViewById(R.id.address1);
        address2=findViewById(R.id.address2);
        contact=findViewById(R.id.contact);
        saveButton=findViewById(R.id.detailsSaveButton);
        editButton=findViewById(R.id.detailsEditButton);
    }
}
