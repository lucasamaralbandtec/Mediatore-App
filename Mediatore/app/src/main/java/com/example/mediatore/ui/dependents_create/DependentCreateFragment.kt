package com.example.mediatore.ui.dependents

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediatore.MainActivity
import com.example.mediatore.R
import com.example.mediatore.conections.ApiConectionUser
import com.example.mediatore.databinding.FragmentDependentCreateBinding
import com.example.mediatore.models.Dependent
import com.example.mediatore.models.ResponsibleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DependentCreateFragment : Fragment() {

    private lateinit var dependentCreateViewModel: DependentCreateViewModel

    private var _binding: FragmentDependentCreateBinding? = null

    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
    : View? {
        dependentCreateViewModel = ViewModelProvider(this).get(DependentCreateViewModel::class.java)

        _binding = FragmentDependentCreateBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val preferences = this.requireActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE)

        val userId = preferences.getInt("IdUser", 0)

        var btn_subscribeDependent: Button = _binding!!.btnSubscribeDependent

        btn_subscribeDependent.setOnClickListener {
            subscribeDependent(userId)
        }

        return root
    }

    private fun navigateBack()
    {
        (activity as MainActivity).changeFragment(R.id.nav_dependents)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun subscribeDependent(userId:Int)
    {
        val _apiUser = ApiConectionUser.createConectionUser()

        var responseSupport = ResponsibleResponse(" ", " ", " ", " ", userId ,  " "," ", " ", " ", " ", " ", false," ")

        val responsibleDataCall: Call<ResponsibleResponse> = _apiUser.getUser(userId)

        responsibleDataCall.enqueue(object : Callback<ResponsibleResponse>
        {
            override fun onResponse(call: Call<ResponsibleResponse>, response: Response<ResponsibleResponse>)
            {
                responseSupport = response.body()!!
            }
            override fun onFailure(call: Call<ResponsibleResponse>, t: Throwable)
            {
                Log.e("log", "${t.message}")
                Toast.makeText(
                    context,
                    "error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        val apiUsers = ApiConectionUser.createConectionUser()

        val etFullName: EditText = _binding!!.etFullNameDependent
        val etCpf: EditText = _binding!!.etCpfDependent

        val etBirthDate: DatePicker = _binding!!.dpDependentDatePicker
        val birthDateYear: String = _binding!!.dpDependentDatePicker.year.toString()
        val birthDateMonth: String = _binding!!.dpDependentDatePicker.month.toString()
        val birthDateDay: String = _binding!!.dpDependentDatePicker.dayOfMonth.toString()

        val etFullNameText = etFullName.text.toString()
        val etCPFText = etCpf.text.toString()

        val df = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val d1: LocalDate = LocalDate.parse("${birthDateDay}-0${birthDateMonth}-${birthDateYear}", df)

        var newDependent = Dependent (etFullNameText, etCPFText, d1, responseSupport)
        val dataCall: Call<Dependent> = apiUsers.createDependent(newDependent)

        dataCall.enqueue(object : Callback<Dependent>
        {
            override fun onResponse(call: Call<Dependent>, response: Response<Dependent>)
            {
                if(response.code() == 200)
                {
                    Toast.makeText(
                        context,
                        "Subscribed with success",
                        Toast.LENGTH_SHORT
                    ).show()

                    navigateBack()
                }
            }
            override fun onFailure(call: Call<Dependent>, t: Throwable)
            {
                Log.e("log", "${t.message}")

                Toast.makeText(
                    context,
                    "error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}