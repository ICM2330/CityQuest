package com.example.cityquest.fragments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cityquest.R
import com.example.cityquest.adapters.PhotoAdapter
import com.example.cityquest.adapters.PhotoItemWrapper
import com.example.cityquest.databinding.FragmentProfileBinding
import com.example.cityquest.decoration.PhotoDecoration
import com.example.cityquest.items.PhotoItem
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.initialize
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.parse.ParseUser
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import java.util.Locale

// Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileBinding
    private lateinit var fullname: TextView
    private lateinit var city: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun retrieveImageByName(imageName: String, imageView: ImageView) {
        Firebase.initialize(context = requireContext())
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )

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

    fun getCityFromLocation(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val geoPoint = GeoPoint(latitude, longitude)
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(
                geoPoint.latitude,
                geoPoint.longitude,
                1 // You can specify the maximum number of results here
            )

            if (!addresses.isNullOrEmpty()) {
                val address: Address = addresses[0]
                // Assuming the city is stored in the locality field, modify this based on your requirements
                return address.locality
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().load(
            requireContext(),
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
        )

        val currentUser = ParseUser.getCurrentUser()

        val emptyImageView: ImageView = requireView().findViewById(R.id.profilePicture)
        retrieveImageByName(currentUser.objectId.toString(), emptyImageView)

        fullname = requireView().findViewById(R.id.nameTextView)
        fullname.text = "${currentUser.get("name")} ${currentUser.get("surname")}"

        city = requireView().findViewById(R.id.city)
        city.text = getCityFromLocation(currentUser.get("latitude").toString().toDouble(), currentUser.get("longitude").toString().toDouble())

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.photoRecyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL // Opcional: Cambia la orientación a horizontal
        recyclerView.layoutManager = layoutManager

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing) // Define el valor de espaciado en dimens.xml
        recyclerView.addItemDecoration(PhotoDecoration(spacingInPixels))

        val photos = listOf(
            PhotoItem("https://tse2.mm.bing.net/th?id=OIP.zHomk1pL8vL0R9gu-3i0NAHaD7&pid=Api&P=0&h=180"),
            PhotoItem("https://www.colombia-travels.com/wp-content/uploads/adobestock-266299444-1.jpeg"),
            PhotoItem("https://interbogotahotel.com/wp-content/uploads/2020/10/turismo-en-bogota-04.png")
            // Agrega más fotos aquí
        )

        val adapter = PhotoAdapter(PhotoItemWrapper.PhotoList(photos))
        recyclerView.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}