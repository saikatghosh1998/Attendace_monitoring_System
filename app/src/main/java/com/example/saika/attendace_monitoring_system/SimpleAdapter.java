package com.example.saika.attendace_monitoring_system;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SimpleAdapter extends BaseAdapter {
    private Context acontext;
    private LayoutInflater layoutInflater;
    private TextView title,description;
    private List<String> name;
    private List<String> roll;
   //private ImageView imageView;
    CircleImageView imageView;

    public SimpleAdapter (Context context, List<String> title, List<String> description){
        acontext=context;
        name=title;
        roll=description;
        layoutInflater=LayoutInflater.from(context);


    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item, null);
        }
        title= convertView.findViewById(R.id.name_id);
        description= convertView.findViewById(R.id.roll_id);
        imageView= convertView.findViewById(R.id.imview_id);
        title.setText(name.get(i));
        description.setText(roll.get(i));

        imageView.setImageResource(R.drawable.student);

        return convertView;
    }


   /* @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        //MultiDex.install(this);
    }*/
}