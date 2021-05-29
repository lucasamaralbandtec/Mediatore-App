package com.example.mediatore.ui.login

import com.example.mediatore.ui.create_account.CreateAccount
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mediatore.MainActivity
import com.example.mediatore.R
import com.example.mediatore.conections.ApiConectionUser
import com.example.mediatore.models.Responsible
import com.example.mediatore.models.ResponsibleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    lateinit var preferences:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preferences = getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val lastUser = preferences.getString("user", null)
        val userId = preferences.getInt("IdUser", 0)

        if(lastUser != "")
        {
            if (lastUser != null)
            {
                getMainActivity(lastUser, userId)
            }
        }
    }

    fun getMainActivity(lastUser: String, userId: Int)
    {
        val mainActivity = Intent(this, MainActivity::class.java)
        mainActivity.putExtra("user", lastUser)
        mainActivity.putExtra("IdUser", userId)
        startActivity(mainActivity)
    }

    fun login(view: View)
    {
        val apiMediatore = ApiConectionUser.createConectionUser()

        val etLogin:EditText = findViewById(R.id.et_login)
        val etPassword:EditText = findViewById(R.id.et_password)

        val login = etLogin.text.toString()
        val password = etPassword.text.toString()

        val simplesUser  = Responsible(" ", " ", " ", "   ", " ", password, " ", login, " ", " ", true, " ")

        val dataCall: Call<ResponsibleResponse> = apiMediatore.login(simplesUser)

        dataCall.enqueue(object : Callback<ResponsibleResponse>
        {
            override fun onResponse(call: Call<ResponsibleResponse>, response: Response<ResponsibleResponse>) {
                if (response.code() == 202)
                {
                    val editor = preferences.edit()
                    editor.putString("user", login)
                    editor.putInt("IdUser", response.body()!!.idResponsavel)
                    editor.commit()

                    getMainActivity(login, response.body()!!.idResponsavel)
                }
                else
                {
                    Toast.makeText(
                        baseContext,
                        "User not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponsibleResponse>, t: Throwable)
            {
                Toast.makeText(
                    baseContext,
                    "error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun goCreateAccountActivity(view: View)
    {
        val createAccountActivity = Intent(this, CreateAccount::class.java)
        startActivity(createAccountActivity)
    }
}