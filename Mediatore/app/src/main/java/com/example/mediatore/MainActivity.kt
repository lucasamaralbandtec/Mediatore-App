package com.example.mediatore

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mediatore.databinding.ActivityMainBinding
import com.example.mediatore.ui.history.HistoryFragment
import com.example.mediatore.ui.login.Login
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_appointment, R.id.nav_history, R.id.nav_dependents, R.id.nav_appointments_create, R.id.nav_dependents_create
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        preferences = getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val lastUser = preferences.getString("user", null)
        val userId = preferences.getInt("IdUser", 0)

        val fragmentHistory = Intent(this, HistoryFragment::class.java)
        fragmentHistory.putExtra("user", lastUser)
        fragmentHistory.putExtra("IdUser", userId)

        if(lastUser == null)
        {
            goLogin()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun changeFragment(idFragment: Int)
    {
        //binding.navView.findNavController().navigate(idFragment)
        findNavController(R.id.nav_host_fragment_content_main).navigate(idFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun goLogin()
    {
        val loginPage = Intent(this, Login::class.java)
        startActivity(loginPage)
    }

    fun loggof(item: MenuItem?)
    {
        val preferences = getSharedPreferences("Auth", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.remove("user")
        editor.remove("IdUser")
        editor.commit()

        goLogin()
    }
}