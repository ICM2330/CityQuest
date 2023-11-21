package com.example.cityquest.fragments

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.cityquest.R
import com.example.cityquest.databinding.FragmentAddPhotoBinding
import org.osmdroid.config.Configuration

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

        binding.aceptar.setOnClickListener() {

        }

        binding.rechazar.setOnClickListener() {

        }
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}