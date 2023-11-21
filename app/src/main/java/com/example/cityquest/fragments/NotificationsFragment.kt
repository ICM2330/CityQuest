package com.example.cityquest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cityquest.R
import com.example.cityquest.databinding.FragmentNotificationsBinding
import com.example.cityquest.items.NotificationItem
import com.example.cityquest.adapters.NotificationAdapter
import org.osmdroid.config.Configuration

class NotificationsFragment : Fragment() {
    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().load(
            requireContext(),
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
        )

        val notificationList = listOf(
            NotificationItem("Nuevo seguidor", "Laura te ha seguido", true, "Hace 5 minutos"),
            NotificationItem("Nueva reacción", "María le ha dado like a tu publicación", false, "Hace 10 minutos"),
            NotificationItem("Nuevo seguidor", "María te ha seguido", false, "Hace 11 minutos")
            // Agrega más elementos de notificación según sea necesario
        )

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = NotificationAdapter(notificationList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}