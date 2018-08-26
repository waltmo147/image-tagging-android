package com.example.jc.myapplication;

import android.arch.paging.PagedListAdapter;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jc.myapplication.model.ImageItem;
import com.example.jc.myapplication.util.JsonUtilities;
import com.example.jc.myapplication.util.NetworkUtilities;

public class ImageItemAdapter extends PagedListAdapter<ImageItem, ImageItemAdapter.ItemViewHolder> {

    private Context mCtx;

    ImageItemAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.rowlayout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ImageItem item = getItem(position);
        holder.imageView.layout(0,0,0,0);

        if (item != null) {
            String imgStr = item.getImage();
            Bitmap img = JsonUtilities.getBitmapFromString(imgStr);
            if (img == null) {
                Log.d("ImageItemAdapter", "onBindViewHolder: " + "image is null at" + position);
            }
            else {
                Log.d("ImageItemAdapter", "onBindViewHolder: " + "width: " + img.getWidth() + "  height" + img.getHeight());

            }

            Glide.with(mCtx)
                    .load(img)
                    .into(holder.imageView);
        }else{
            Toast.makeText(mCtx, "Item is null", Toast.LENGTH_LONG).show();
        }
    }

    private static DiffUtil.ItemCallback<ImageItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ImageItem>() {
                @Override
                public boolean areItemsTheSame(ImageItem oldItem, ImageItem newItem) {
                    return oldItem.getFilename() == newItem.getFilename();
                }

                @Override
                public boolean areContentsTheSame(ImageItem oldItem, ImageItem newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.more_image);
        }
    }
}
