package com.codingblocksmodules.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codingblocksmodules.tictactoe.databinding.ActivityMain2Binding
import com.codingblocksmodules.tictactoe.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        binding.btn.setOnClickListener {
            val i = Intent(this@MainActivity2 , MainActivity::class.java)
            val name1 = binding.etName1.text.toString()
            val name2 = binding.etName2.text.toString()
            Log.d("TAG", "onCreate: $name1 and $name2")
            i.putExtra("Name1" , name1)
            i.putExtra("Name2" , name2)
            startActivity(i)
        }
    }
}