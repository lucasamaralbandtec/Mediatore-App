package com.example.mediatore.ui.appointment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediatore.MainActivity
import com.example.mediatore.R
import com.example.mediatore.conections.ApiConectionAppointment
import com.example.mediatore.databinding.FragmentAppointmentBinding
import com.example.mediatore.models.Appointment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentFragment : Fragment() {

    private lateinit var appointmentViewModel: AppointmentViewModel
    private var _binding: FragmentAppointmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView
                (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appointmentViewModel =
            ViewModelProvider(this).get(AppointmentViewModel::class.java)

        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        var layoutlist: LinearLayout = _binding!!.llAppointList

        val preferences = this.requireActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE)

        val userId = preferences.getInt("IdUser", 0)
        val user = preferences.getString("user", null)

        getAppointments(userId, layoutlist)

        var btn_makeAppointment: Button = _binding!!.btnMakeAppoint

        btn_makeAppointment.setOnClickListener { goAppointmentCreate() }

        return root
    }

    fun goAppointmentCreate()
    {
        (activity as MainActivity).changeFragment(R.id.nav_appointments_create)
    }

    fun getAppointments(UserId:Int, listLayout: LinearLayout)
    {
        val medicText:String = getString(R.string.medic)
        val especializationText:String = getString(R.string.especialization)
        val hourText:String = getString(R.string.hour)
        val dateText:String = getString(R.string.date)

        val apiAppointments = ApiConectionAppointment.createConectionAppointment()

        val dataCall: Call<List<Appointment>> = apiAppointments.getAppointmentById(UserId)

        dataCall.enqueue(object : Callback<List<Appointment>>
        {
            override fun onResponse(call: Call<List<Appointment>>, response: Response<List<Appointment>>)
            {
                if(response.code() == 404)
                {
                    val tvAppointment = TextView(context)
                    tvAppointment.text = "You don't have any appointments"
                    tvAppointment.setTextColor(Color.parseColor("#128AB8"))
                    tvAppointment.setTextSize(1, 20.0F)
                    tvAppointment.setPadding(50,20,0,0);
                    tvAppointment.setBackgroundResource(R.drawable.bk_historic_background)
                    listLayout.addView(tvAppointment)
                }
                else if (response.code() == 200)
                {
                    response.body()?.forEach {
                        val tvAppointment = TextView(context)
                        tvAppointment.text =
                            "${medicText}: ${it.nomeMedico} " +
                                    "\n${especializationText}: ${it.especializacao} " +
                                    "\n${hourText}: ${it.data} " +
                                    "\n${dateText}: ${it.horario}\n"

                        tvAppointment.setTextColor(Color.parseColor("#128AB8"))
                        tvAppointment.setTextSize(1, 20.0F)
                        tvAppointment.gravity = 0;
                        tvAppointment.setPadding(50,20,0,0);
                        tvAppointment.setBackgroundResource(R.drawable.bk_historic_background)
                        listLayout.addView(tvAppointment)
                    }
                }
            }

            override fun onFailure(call: Call<List<Appointment>>, t: Throwable)
            {
                Log.e("log", "${t.message}")

                Toast.makeText(
                    context,
                    "Erro na chamada: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}