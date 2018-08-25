//package com.example.jc.myapplication;
//
//import android.graphics.Bitmap;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.List;
//
//// Create the basic adapter extending from RecyclerView.Adapter
//// Note that we specify the custom ViewHolder which gives us access to our views
//public class ShowImagesAdapter extends
//        RecyclerView.Adapter<ShowImagesAdapter.ViewHolder> {
//
//    // Store a member variable for the contacts
//    private List<Bitmap> mImages;
//
//    // Pass in the contact array into the constructor
//    public ShowImagesAdapter(List<Bitmap> images) {
//        this.mImages = images;
//    }
//
//    // Provide a direct reference to each of the views within a data item
//    // Used to cache the views within the item layout for fast access
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // Your holder should contain a member variable
//        // for any view that will be set as you render a row
//        public ImageView imageView;
//
//        // We also create a constructor that accepts the entire item row
//        // and does the view lookups to find each subview
//        public ViewHolder(View itemView) {
//            // Stores the itemView in a public final member variable that can be used
//            // to access the context from any ViewHolder instance.
//            super(itemView);
//
//            imageView = (ImageView) itemView.findViewById(R.id.more_image);
//        }
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        View contactView = inflater.inflate(R.layout.rowlayout, parent, false);
//
//        ViewHolder viewHolder = new ViewHolder(contactView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        Bitmap img = mImages.get(position);
//        ImageView imageViewObject = viewHolder.imageView;
//        imageViewObject.setImageBitmap(img);
//
////        Button button = viewHolder.messageButton;
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mImages.size();
//    }
//}
