package com.example.cityquest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cityquest.items.PhotoItem
import com.example.cityquest.R

sealed class PhotoItemWrapper {
    data class PhotoList(val photos: List<PhotoItem>) : PhotoItemWrapper()
    data class SinglePhoto(val photo: PhotoItem) : PhotoItemWrapper()
}

class PhotoAdapter(private val photoItemWrapper: PhotoItemWrapper) :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val photoWrapper = photoItemWrapper) {
            is PhotoItemWrapper.PhotoList -> {
                val photo = photoWrapper.photos[position]
                Glide.with(holder.itemView)
                    .load(photo.imageUrl)
                    .into(holder.photoImageView)
            }
            is PhotoItemWrapper.SinglePhoto -> {
                val photo = photoWrapper.photo
                Glide.with(holder.itemView)
                    .load(photo.imageUrl)
                    .into(holder.photoImageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return when (photoItemWrapper) {
            is PhotoItemWrapper.PhotoList -> photoItemWrapper.photos.size
            is PhotoItemWrapper.SinglePhoto -> 1
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)
    }
}
