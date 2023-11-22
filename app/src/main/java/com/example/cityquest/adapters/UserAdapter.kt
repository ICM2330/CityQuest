package com.example.cityquest.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cityquest.R
import com.example.cityquest.activities.ChatActivity
import com.example.cityquest.models.User
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.initialize
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserAdapter(context: Context, users: List<User>) :
    ArrayAdapter<User>(context, 0, users) {
    var objid: String? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)

        val user = getItem(position)

        val nameTextView = view.findViewById<TextView>(R.id.userName)
        val emailTextView = view.findViewById<TextView>(R.id.TextMsg)
        val userImageView = view.findViewById<ImageView>(R.id.userImage)

        nameTextView.text = user?.nombre
        emailTextView.text = user?.email
        var imageID = user?.imageUrl
        objid = imageID

        if (userImageView != null){
            retrieveImageByName(imageID.toString(), userImageView)
            //Glide.with(context).load(imageID).into(userImageView)
        }

        // Configura el OnClickListener para cada ítem de la lista
        view.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java).apply {
                putExtra("nombre", user?.nombre)
                putExtra("email", user?.email)
                putExtra("imageUrl", user?.imageUrl)
            }
            context.startActivity(intent)
        }
        return view
    }

    private fun retrieveImageByName(imageName: String, imageView: ImageView) {
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference
        val imageRef: StorageReference = storageRef.child("images/$imageName.png")

        imageRef.downloadUrl
            .addOnSuccessListener { uri ->
                Glide.with(imageView.context)
                    .load(uri)
                    .into(imageView)
            }
            .addOnFailureListener { exception ->
                println("Error retrieving image: ${exception.message}")
            }
    }
}