package com.example.jc.myapplication;

import android.content.Context;
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
public class TreeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FlatItem> results;
    private LayoutInflater mInflater;

    public TreeAdapter(Context context, ArrayList<FlatItem> results) {
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
        FlatItem item = (FlatItem) getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.tree_list_item, parent, false);
        }

        TextView label = (TextView) convertView.findViewById(R.id.labelTree);
        TextView score = (TextView) convertView.findViewById(R.id.scoreFlat);
        TextView scoreTree = (TextView) convertView.findViewById(R.id.scoreTree);
        ImageView imageView = convertView.findViewById(R.id.imageViewTree);

        label.setText(item.getLabel());
        score.setText(item.getScore());
        scoreTree.setText(item.getScoreTree());
        imageView.setImageBitmap(item.getImg());

        return convertView;
    }
}
