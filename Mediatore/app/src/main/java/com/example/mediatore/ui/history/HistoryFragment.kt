package com.example.mediatore.ui.history

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mediatore.R
import com.example.mediatore.conections.ApiConectionHistory
import com.example.mediatore.databinding.FragmentHistoryBinding
import com.example.mediatore.models.History
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
    : View? {
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        var listLayout: LinearLayout = _binding!!.llHistList
        val root: View = binding.root

        val preferences = this.requireActivity().getSharedPreferences("Auth", Context.MODE_PRIVATE)

        val userId = preferences.getInt("IdUser", 0)

        getHistory(userId, listLayout)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getHistory(userId:Int, listLayout:LinearLayout)
    {
        //STRINGS
        val medicText:String = getString(R.string.medic)
        val especializationText:String = getString(R.string.especialization)
        val hourText:String = getString(R.string.hour)
        val dateText:String = getString(R.string.date)


        //CREATE HISTORY
        val apiHistory = ApiConectionHistory.createConectionHistory()
        val create: Call<Void> = apiHistory.createHistoryById(userId)

        create.enqueue(object : Callback<Void>
        {
            override fun onResponse(call: Call<Void>, response: Response<Void>)
            {}
            override fun onFailure(call: Call<Void>, t: Throwable)
            {
                Toast.makeText(
                    context,
                    "error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        //GET HISTORY
        val dataCall: Call<List<History>> = apiHistory.getHistoryById(userId)

        dataCall.enqueue(object : Callback<List<History>>
        {
            override fun onResponse(call: Call<List<History>>, response: Response<List<History>>)
            {
                response.body()?.forEach {
                    val tvHistory = TextView(context)
                    tvHistory.text =
                        "${medicText}: ${it.nomeMedico} " +
                                "\n${especializationText}: ${it.especializacao} " +
                                "\n${hourText}: ${it.data} " +
                                "\n${dateText}: ${it.horario}\n"
                    tvHistory.setTextColor(Color.parseColor("#128AB8"))
                    tvHistory.setTextSize(1, 20.0F)
                    tvHistory.setPadding(50,20,0,0);
                    tvHistory.setBackgroundResource(R.drawable.bk_historic_background)
                    listLayout.addView(tvHistory)
                }
            }

            override fun onFailure(call: Call<List<History>>, t: Throwable)
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