package com.example.entregaproyecto

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.entregaproyecto.databinding.ActivityNuevaLocacionBinding
import java.io.File

class NuevaLocacion : AppCompatActivity() {

    private lateinit var binding: ActivityNuevaLocacionBinding

    val getContentGallery = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            loadImage(it!!)
        }
    )

    val getContentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture(),
        ActivityResultCallback {
            if(it){
                loadImage(cameraUri)
            }
        })

    lateinit var cameraUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevaLocacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener {
            getContentGallery.launch("image/*")
        }

        binding.cameraButton.setOnClickListener {
            val file = File(getFilesDir(), "picFromCamera");
            cameraUri = FileProvider.getUriForFile(baseContext,baseContext.packageName + ".fileprovider", file)
            getContentCamera.launch(cameraUri)
        }
    }

    fun loadImage(uri : Uri){
        val imageStream = getContentResolver().openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        binding.image.setImageBitmap(bitmap)
    }
}