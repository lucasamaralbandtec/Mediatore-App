package com.example.mediatore.ui.create_account

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mediatore.R
import com.example.mediatore.conections.ApiConectionUser
import com.example.mediatore.models.Responsible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccount : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

    }

    fun createAccount(view: View)
    {
        val apiMediatore = ApiConectionUser.createConectionUser()

        val etLogin:EditText = findViewById(R.id.et_login_create_account)
        val etEmail:EditText = findViewById(R.id.et_email)
        val etCpf:EditText = findViewById(R.id.et_cpf)
        val etPassword:EditText = findViewById(R.id.et_password_create_account)

        val email = etEmail.text.toString()
        val login = etLogin.text.toString()
        val cpf = etCpf.text.toString()
        val senha = etPassword.text.toString()

        val simpleUser  = Responsible(" ", " ", " ", " ", " ",  senha," ", email, login, cpf, false," ")

        apiMediatore.createUser(simpleUser).enqueue(object : Callback<Responsible> {
            override fun onResponse(call: Call<Responsible>, response: Response<Responsible>) {
                if (response.code() != 404)
                {
                    Toast.makeText(baseContext, "Um ou mais campos vazios", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(baseContext, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish();
                }
            }

            override fun onFailure(call: Call<Responsible>, t: Throwable)
            {
                finish();
                Toast.makeText(baseContext, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}