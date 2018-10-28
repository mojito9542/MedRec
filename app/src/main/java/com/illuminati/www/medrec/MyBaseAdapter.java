package com.illuminati.www.medrec;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MyBaseAdapter extends ArrayAdapter<medilist> {
    private ArrayList<medilist> myList;
    LayoutInflater inflater;
    Context context;


    public MyBaseAdapter(Context context, ArrayList<medilist> myList) {
        super(context, -1, myList);
        inflater=LayoutInflater.from(context);
        this.myList=myList;
        this.context=context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dummy, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        Animation animation = null;
        animation = AnimationUtils.loadAnimation(context, R.anim.action);
        animation.setDuration(200);
        convertView.startAnimation(animation);
        animation = null;
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String strDate = dateFormat.format(myList.get(position).getExp());
         mViewHolder.exp.setText("exp date: "+strDate);
         mViewHolder.name.setText(myList.get(position).getName());
        mViewHolder.dose.setText(myList.get(position).getDose());
        mViewHolder.days.setText(myList.get(position).getDays());
        Date curr=new Date();
        Date init=myList.get(position).getDate();
        int diffInDays = (int) ((curr.getTime() - init.getTime()) / (1000 * 60 * 60 * 24));
        int dosday= Integer.valueOf(myList.get(position).getDays());
        if(diffInDays>=dosday)
        {
            mViewHolder.inc.setVisibility(View.VISIBLE);
            mViewHolder.rec.setVisibility(View.VISIBLE);
            mViewHolder.inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,Medinfo.class));
                }
            });
            mViewHolder.rec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,Main2Activity.class));
                }
            });

        }

        else
        {
            mViewHolder.days.setText(String.valueOf(dosday-diffInDays));
        }
        return convertView;
    }


    private class MyViewHolder {

        TextView exp,name,dose,days;
        Button inc,rec;
        private MyViewHolder(View view) {
            exp= view.findViewById(R.id.exp);
            name=view.findViewById(R.id.name);
            dose=view.findViewById(R.id.dose);
            days=view.findViewById(R.id.days);
            inc=view.findViewById(R.id.increase);
            rec=view.findViewById(R.id.recycle);

        }
    }
}


