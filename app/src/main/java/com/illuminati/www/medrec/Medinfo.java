package com.illuminati.www.medrec;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Medinfo extends AppCompatActivity {

    EditText dd,mm,yy,days,dose,name,inv;
    Button submit;
    FirebaseFirestore db;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medinfo);
        dd=findViewById(R.id.dd);
        mm=findViewById(R.id.mm);
        yy=findViewById(R.id.yy);
        days=findViewById(R.id.days);
        dose=findViewById(R.id.dose);
        name=findViewById(R.id.name);
        inv=findViewById(R.id.inv);
        submit=findViewById(R.id.sbmt);
        db = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d=Integer.valueOf(dd.getText().toString().trim());
                int m=Integer.valueOf(mm.getText().toString().trim())-1;
                int y=Integer.valueOf(yy.getText().toString().trim())-1900;
                Date date= new Date(y,m,d);
                Date now= new Date(0);
                if(date.equals(now)||date.before(now))
                    Toast.makeText(Medinfo.this, "Medicine Expired!! Cannot except medicine", Toast.LENGTH_SHORT).show();
                 else
                {
                    Map<String, Object> med = new HashMap<>();
                    med.put("name", name.getText().toString().trim());
                    med.put("days", days.getText().toString().trim());
                    med.put("dose", dose.getText().toString().trim());
                    med.put("expiry",date);
                    med.put("inv",inv.getText().toString().trim());
                    db.collection("meds")
                            .add(med)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(Medinfo.this, "Medicine added successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                    Toast.makeText(Medinfo.this, "Unable to upload medicine", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });




    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Medinfo.this,MainActivity.class));
    }
}
