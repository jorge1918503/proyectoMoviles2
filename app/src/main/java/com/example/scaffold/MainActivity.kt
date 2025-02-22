package com.example.scaffold

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        // Comprobar la versión de Android
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S)
        {
            // Menor que Android 12
            startActivity(Intent(this, SplashActivity::class.java))
        }
        //finish()
        setContentView(R.layout.activity_main)

        //splash.setKeepOnScreenCondition{true}

    }

    //Nuevo método para configurar el componente Navigation
    override fun onSupportNavigateUp(): Boolean
    {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}