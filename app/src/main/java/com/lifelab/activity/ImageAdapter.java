package com.lifelab.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lifelab.R;

/**
 * Created by E9949942 on 12/26/2016.
 */
public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    private final Integer[] experiment;
    private LayoutInflater inflater;

    public ImageAdapter(Context mContext, Integer[] experiment) {
        this.mContext = mContext;
        this.experiment = experiment;
       // this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.inflater = LayoutInflater.from(this.mContext);
    }
    @Override
    public int getCount() {
        return experiment.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
      ///  View grid;
        View imageView;
        ImageView imgView;
        if (convertView == null) {

            ///grid = new View(mContext);
          // imageView = new View(mContext);
           ///grid = inflater.inflate(R.layout.single_grid, parent,false);
            imageView = inflater.inflate(R.layout.single_grid, parent,false);
          ////  imageView.setLayoutParams(new GridView.LayoutParams(300,300));
           //// imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
           //// imageView.setPadding(5,5,5,5);
            //TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            imgView=(ImageView)imageView.findViewById(R.id.grid_view);
           // textView.setText(experiment[position]);
           // int excp = experiment[position];
            imgView.setImageResource(experiment[position]);
          ////  imageView.setImageResource(experiment[position]);
           // Log.i("here ","re "+position);
           //button.setText(experiment[position]);

        } else {
           /// grid = (View) convertView;
            imgView = (ImageView)convertView;
            imgView.setImageResource(experiment[position]);
          ////  imageView = (ImageView) convertView;
          ////  imageView.setImageResource(experiment[position]);
        }

        ///return grid;
        return imgView;
    }
}
