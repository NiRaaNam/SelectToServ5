package com.example.niraanam.selecttoserv5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<Spacecraft> spacecrafts;

    public CustomAdapter(Context c, ArrayList<Spacecraft> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
    }

    @Override
    public int getCount() {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int i) {
        return spacecrafts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            //INFLATE CUSTOM LAYOUT
            view= LayoutInflater.from(c).inflate(R.layout.rows,viewGroup,false);
        }

        final Spacecraft s= (Spacecraft) this.getItem(i);

        TextView nameTxt= (TextView) view.findViewById(R.id.Itemname);
        ImageView img= (ImageView) view.findViewById(R.id.icon);

        //BIND DATA
        nameTxt.setText(s.getName());
        Picasso.with(c).load(s.getUri()).placeholder(R.drawable.ic_autorenew_black_24dp).into(img);

        //VIEW ITEM CLICK
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, s.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}