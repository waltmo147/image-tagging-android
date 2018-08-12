package com.example.jc.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jc.myapplication.model.FlatItem;

import java.util.ArrayList;

/**
 * Created by apple on 5/4/18.
 */
public class ImgAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Bitmap> results;
    private LayoutInflater mInflater;

    public ImgAdapter(Context context, ArrayList<Bitmap> results) {
        this.context = context;
        this.results = results;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override

    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap item = (Bitmap) getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.img_list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageItem);

        imageView.setImageBitmap(item);

        return convertView;
    }
}
