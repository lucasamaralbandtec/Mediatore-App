package com.example.mediatore.ui.dependents

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
import com.example.mediatore.conections.ApiConectionUser
import com.example.mediatore.databinding.FragmentDependentBinding
import com.example.mediatore.models.Dependent
import com.example.mediatore.models.DependentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DependentFragment : Fragment() {

    private lateinit var dependentViewModel: DependentViewModel

    private var _binding: FragmentDependentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
    : View? {
        dependentViewModel = ViewModelProvider(this).get(DependentViewModel::class.java)

        _binding = FragmentDependentBinding.inflate(inflater, container, false)

        var layoutLista: LinearLayout = _binding!!.llDependentList
        val root: View = binding.root

        val preferences = this.requireActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE)

        val userId = preferences.getInt("IdUser", 0)

        var btn_subscribeDependent: Button = _binding!!.btnSubscribeDependent

        btn_subscribeDependent.setOnClickListener {
            goDependentSubscribing()
        }

        getDependents(userId, layoutLista)

        return root
    }

    fun goDependentSubscribing()
    {
        (activity as MainActivity).changeFragment(R.id.nav_dependents_create)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    fun getDependents(userId:Int, listLayout:LinearLayout)
    {
        val name:String = getString(R.string.name)
        val cpf:String = getString(R.string.cpf)
        val birthDateText:String = getString(R.string.birthDate)

        val apiUsers = ApiConectionUser.createConectionUser()

        //CONSULTA HISTÓRICO
        val dataCall: Call<List<DependentResponse>> = apiUsers.getDependents(userId)

        dataCall.enqueue(object : Callback<List<DependentResponse>>
        {
            override fun onResponse(call: Call<List<DependentResponse>>, response: Response<List<DependentResponse>>)
            {
                response.body()?.forEach {
                    val tvDependent = TextView(context)
                    tvDependent.text = "${name}: ${it.nomeDependente} " + "\n${cpf}: ${it.cpfDependente} " + "\n${birthDateText}: ${it.dataNascimento.toString()}"
                    tvDependent.setTextColor(Color.parseColor("#128AB8"))
                    tvDependent.setTextSize(1, 20.0F)
                    tvDependent.setPadding(50,20,0,0);
                    tvDependent.setBackgroundResource(R.drawable.bk_historic_background)
                    listLayout.addView(tvDependent)
                }
            }

            override fun onFailure(call: Call<List<DependentResponse>>, t: Throwable)
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
}