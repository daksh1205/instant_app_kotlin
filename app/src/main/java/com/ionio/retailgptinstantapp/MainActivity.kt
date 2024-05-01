package com.ionio.retailgptinstantapp


import android.content.Intent

import android.os.Bundle

import android.widget.Button

import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            // Start the ThankYouActivity
            startActivity(Intent(this, ThankYouActivity::class.java))
        }
    }
}