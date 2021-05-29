package com.example.mediatore.ui.appointment_create

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediatore.databinding.FragmentAppointmentCreateBinding

class AppointmentCreateFragment : Fragment() {

    private lateinit var appointmentViewModel: AppointmentCreateViewModel
    private var _binding: FragmentAppointmentCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        appointmentViewModel = ViewModelProvider(this).get(AppointmentCreateViewModel::class.java)

        _binding = FragmentAppointmentCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //var layoutlist: LinearLayout = _binding!!.llAppointList

        val preferences = this.requireActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE)

        val userId = preferences.getInt("IdUser", 0)
        val usuario = preferences.getString("user", null)

        return root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}