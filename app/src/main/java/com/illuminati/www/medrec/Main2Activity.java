package com.illuminati.www.medrec;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    FirebaseFirestore db;
    HospitalAdapter adapter;
    private String TAG;
    hoslist[] md;
    public ListView r1;
    public ArrayList<hoslist> myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db = FirebaseFirestore.getInstance();
        r1=findViewById(R.id.r1);
        myList=new ArrayList<>();
        db.collection("Hospitals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()&& task.getResult().size()>0) {
                            md= new hoslist[task.getResult().size()+1];
                            int i=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                md[i] = new hoslist();
                                md[i].setAddress(document.getString("address"));
                                md[i].setName(document.getString("name"));
                                i++;
                            }
                            for ( i = 0; i <task.getResult().size()-1; i++) {
                                myList.add(md[i]);
                            }

                            adapter = new HospitalAdapter(getApplicationContext(), myList);
                            r1.setAdapter(adapter);

                        }


                        else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(Main2Activity.this, "No hospitals around you", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    }

