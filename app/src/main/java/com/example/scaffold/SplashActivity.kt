package com.example.scaffold

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.scaffold.databinding.ActivitySplashBinding
import com.example.scaffold.fragment.LoginFragment

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root

        enableEdgeToEdge()
        setContentView(view)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginFragment::class.java))
            finish()
        }, 2000) // 2 segundos
    }
}