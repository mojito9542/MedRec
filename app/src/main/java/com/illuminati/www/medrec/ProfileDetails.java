package com.illuminati.www.medrec;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ProfileDetails extends Activity {
    private EditText name,address1,address2,contact;
    private Button saveButton,cancelButton;
    FirebaseFirestore db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        name=findViewById(R.id.enterName);
        address1=findViewById(R.id.address1);
        address2=findViewById(R.id.address2);
        contact=findViewById(R.id.contact);
        saveButton=findViewById(R.id.detailsSaveButton);
        cancelButton=findViewById(R.id.detailsBackButton);
        db = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        db.collection(auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()&& task.getResult().size()>0) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               name.setText(document.getString("name"));
                                address1.setText(document.getString("ad1"));
                                address2.setText(document.getString("ad2"));
                                contact.setText(document.getString("contact"));

                            }


                        }


                        else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(ProfileDetails.this, "Enter Your Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> user = new HashMap<>();
                user.put("name", name.getText().toString().trim());
                user.put("ad1", address1.getText().toString().trim());
                user.put("ad2", address2.getText().toString().trim());
                user.put("contact",contact.getText().toString().trim());
                Boolean success=db.collection(auth.getCurrentUser().getUid()).document(auth.getCurrentUser().getUid()+"d")
                        .update(user).isSuccessful();
                if(success)
                {
                    Toast.makeText(ProfileDetails.this, "profile updated successfully", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ProfileDetails.this, "profile updation failed", Toast.LENGTH_SHORT).show();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileDetails.this,MainActivity.class));
                finish();
            }
        });

    }

}
