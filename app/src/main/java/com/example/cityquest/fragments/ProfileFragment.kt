package com.example.cityquest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cityquest.R
import com.example.cityquest.adapters.PhotoAdapter
import com.example.cityquest.databinding.FragmentProfileBinding
import org.osmdroid.config.Configuration
import com.example.cityquest.decoration.PhotoDecoration
import com.example.cityquest.items.PhotoItem

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().load(
            requireContext(),
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
        )

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

        val adapter = PhotoAdapter(photos)
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