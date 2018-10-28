package com.illuminati.www.medrec;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Medinfo extends AppCompatActivity implements View.OnClickListener {

    EditText dd,mm,yy,days,dose,name,inv;
    Button submit;
    FirebaseFirestore db;
    FirebaseAuth auth;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medinfo);
        dd=findViewById(R.id.dd);
        mm=findViewById(R.id.mm);
        yy=findViewById(R.id.yy);
        LinearLayout ll=findViewById(R.id.ll);
        ll.getBackground().setAlpha(100);
        auth=FirebaseAuth.getInstance();
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
                    med.put("date",new Date());
                    med.put("expiry",date);
                    med.put("inv",inv.getText().toString().trim());
                    db.collection("meds"+auth.getCurrentUser().getUid())
                            .add(med)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(Medinfo.this, "Medicine added successfully", Toast.LENGTH_SHORT).show();
                                switch(Integer.valueOf(dose.getText().toString().trim()))
                                {
                                    case 6: findViewById(R.id.dos6).setVisibility(View.VISIBLE);
                                        findViewById(R.id.dos6).setOnClickListener(Medinfo.this);
                                    case 5: findViewById(R.id.dos5).setVisibility(View.VISIBLE);
                                        findViewById(R.id.dos5).setOnClickListener(Medinfo.this);
                                    case 4:findViewById(R.id.dos4).setVisibility(View.VISIBLE);
                                        findViewById(R.id.dos4).setOnClickListener(Medinfo.this);
                                    case 3: findViewById(R.id.dos3).setVisibility(View.VISIBLE);
                                        findViewById(R.id.dos3).setOnClickListener(Medinfo.this);
                                    case 2: findViewById(R.id.dos2).setVisibility(View.VISIBLE);
                                        findViewById(R.id.dos2).setOnClickListener(Medinfo.this);
                                    case 1: findViewById(R.id.dos1).setVisibility(View.VISIBLE);
                                        findViewById(R.id.dos1).setOnClickListener(Medinfo.this);
                                    break;
                                    default:
                                        Toast.makeText(Medinfo.this, "number of dosage too high", Toast.LENGTH_SHORT).show();

                                }
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
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()!=R.id.sbmt)
        {
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getFragmentManager(),"TimePicker");
        }
    }
}
