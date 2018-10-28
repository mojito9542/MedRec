package com.illuminati.www.medrec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * @author Mojito9542
 * @verson Created on 28-10-2018.
 */
public class HospitalAdapter extends ArrayAdapter<hoslist> {
        private ArrayList<hoslist> myList;
        LayoutInflater inflater;
        Context context;



        public HospitalAdapter(Context context, ArrayList<hoslist> myList) {
            super(context, -1, myList);
            inflater=LayoutInflater.from(context);
            this.myList=myList;
            this.context=context;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final viewHolder mViewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.hospital_dummy, parent, false);
                mViewHolder = new viewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (viewHolder) convertView.getTag();
            }
            Animation animation = null;
            animation = AnimationUtils.loadAnimation(context, R.anim.action);
            animation.setDuration(200);
            convertView.startAnimation(animation);
            animation = null;
            mViewHolder.name.setText(myList.get(position).getName());
            mViewHolder.add.setText(myList.get(position).getAddress());
            mViewHolder.messButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "details sent to"+myList.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
        private class viewHolder {

            TextView name,add;
            Button messButton;
            private viewHolder(View view) {
                name=view.findViewById(R.id.hospitalText);
                add=view.findViewById(R.id.hospitalAddress);
                messButton=view.findViewById(R.id.messageSent);

            }
        }
    }




