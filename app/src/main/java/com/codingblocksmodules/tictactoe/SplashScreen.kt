package com.codingblocksmodules.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codingblocksmodules.tictactoe.databinding.ActivityMain2Binding
import com.codingblocksmodules.tictactoe.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(binding.root)
        binding.ivLogo.alpha =0f
        binding.textView.alpha = 0f
        binding.textView.animate().setDuration(3000).alpha(1f)
        binding.ivLogo.animate().setDuration(3000).alpha(1f).withEndAction{
            val i = Intent(this , MainActivity2::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}