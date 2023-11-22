package com.example.cityquest.fragments

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.example.cityquest.R
import com.example.cityquest.databinding.FragmentAddPhotoBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.parse.ParseUser
import org.osmdroid.config.Configuration
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPhotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPhotoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAddPhotoBinding
    private lateinit var uriUpload : Uri
    lateinit var fotoSubida: ImageView
    private var imageSelected = false
    lateinit var camera: Button
    lateinit var gallery: Button
    lateinit var aceptar: Button
    lateinit var cancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().load(
            requireContext(),
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
        )

        val latitude = requireActivity().intent.getDoubleExtra(
            "latitude",
            0.0
        ) // 0.0 is a default value if the extra is not found
        val longitude = requireActivity().intent.getDoubleExtra("longitude", 0.0)

        val imageView = requireView().findViewById<ImageView>(R.id.fotoSubida)

        camera = requireView().findViewById(R.id.camera)
        gallery = requireView().findViewById(R.id.gallery)

        gallery.setOnClickListener {
            getContentGallery.launch("image/*")
        }

        camera.setOnClickListener {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }

        val latitudText = requireView().findViewById<TextView>(R.id.latitud)
        val longitudeText = requireView().findViewById<TextView>(R.id.longitud)
        val formattedLatitud = String.format("%.2f", latitude)
        val formattedLongitud = String.format("%.2f", longitude)

        latitudText.text = "      Latitude: $formattedLatitud"
        longitudeText.text = "      Longitude: $formattedLongitud"

        val imageUriString = requireActivity().intent.getStringExtra("imageUri")
        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            val imageStream = requireContext().contentResolver.openInputStream(imageUri)
            val decodedBitmap = BitmapFactory.decodeStream(imageStream)
            imageView.setImageBitmap(decodedBitmap)
        }

        val usuarioEditText = requireView().findViewById<EditText>(R.id.usuario)
        val userText = usuarioEditText.text.toString()

        aceptar = requireView().findViewById(R.id.aceptar)
        aceptar.setOnClickListener() {
            uploadFirebaseImage(uriUpload)
        }
        cancelar = requireView().findViewById(R.id.rechazar)
        cancelar.setOnClickListener() {
        }
    }

    private fun loadImage(uri: Uri) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            if (bitmap != null) {
                fotoSubida = requireView().findViewById(R.id.fotoSubida)
                fotoSubida.setImageBitmap(rotateImageIfRequired(bitmap, uri))
                uriUpload = uri
            } else {
                Toast.makeText(requireContext(), "Error decoding image", Toast.LENGTH_SHORT).show()
            }

            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error loading image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadFirebaseImage(uriUpload: Uri) {
        val currentUser = ParseUser.getCurrentUser()
        val objectId = currentUser?.objectId
        val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("places/${objectId}.png")

        storageRef.putFile(uriUpload)
            .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                val downloadUrl = taskSnapshot.metadata?.reference?.downloadUrl
                downloadUrl?.addOnSuccessListener { uri ->
                    println("Image uploaded. URL: $uri")
                }
            }
            .addOnFailureListener { exception: Exception ->
                println("Error uploading: ${exception.message}")
            }
    }

    private fun rotateImageIfRequired(bitmap: Bitmap, uri: Uri): Bitmap {
        val ei = ExifInterface(requireContext().contentResolver.openInputStream(uri)!!)

        return when (ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270f)
            else -> bitmap
        }
    }

    private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
        // Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private val getContentGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            loadImage(it!!)
            uriUpload = it
            imageSelected = true
        }

    private val getContentCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                loadImage(uriUpload)
                imageSelected = true
            } else {
                Toast.makeText(requireContext(), "Failed to capture image", Toast.LENGTH_SHORT).show()
            }
        }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
            try {
                val photoFile = createImageFile()
                if (photoFile != null) {
                    uriUpload = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.cityquest.fileprovider",
                        photoFile
                    )

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriUpload)
                    getContentCamera.launch(uriUpload)
                } else {
                    Toast.makeText(requireContext(), "Error creating image file", Toast.LENGTH_SHORT).show()
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        } else {
            Toast.makeText(requireContext(), "No camera app found", Toast.LENGTH_SHORT).show()
        }
    }
}