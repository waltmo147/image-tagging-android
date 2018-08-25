package com.example.jc.myapplication

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
class ShowImagesAdapter// Pass in the contact array into the constructor
(// Store a member variable for the contacts
        private val mImages: List<Bitmap>) : RecyclerView.Adapter<ShowImagesAdapter.ViewHolder>() {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder// We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    (itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        var imageView: ImageView

        init {

            imageView = itemView.findViewById<View>(R.id.more_image) as ImageView
        }// Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val contactView = inflater.inflate(R.layout.rowlayout, parent, false)

        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val img = mImages[position]
        val imageViewObject = viewHolder.imageView
        imageViewObject.setImageBitmap(img)

        //        Button button = viewHolder.messageButton;

    }

    override fun getItemCount(): Int {
        return mImages.size
    }
}
