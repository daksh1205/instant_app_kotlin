package com.ionio.retailgptinstantapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.database

class DetailsActivity : AppCompatActivity() {
    private lateinit var userKey: String
    private lateinit var tvTitle: TextView
    private lateinit var tvFirstName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvIndustry: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvCompanyName: TextView

    private val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        init()
    }

    private fun init() {
        tvTitle = findViewById(R.id.tvTitle)
        tvFirstName = findViewById(R.id.tvFirstName)
        tvIndustry = findViewById(R.id.tvIndustry)
        tvPhone = findViewById(R.id.tvPhone)
        tvEmail = findViewById(R.id.tvEmail)
        tvCompanyName = findViewById(R.id.tvCompanyName)
        userKey = intent.getStringExtra("userKey").toString()
        val userRef = database.getReference("users").child(userKey)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userRef.get().addOnCompleteListener {
            val data = it.result.value as HashMap<*, *>
            val firstName = data["name"] as String
            val email = data["email"] as String
            val phone = data["phone"] as String
            val companyName = data["companyName"] as String
            val industry = data["industry"] as String?
            tvTitle.text = "Yaay! ${firstName.split(" ")[0]} your details are saved as below"
            tvFirstName.text = "Name: $firstName"
            tvEmail.text = "Email: $email"
            tvPhone.text = "Phone: $phone"
            tvCompanyName.text = "Company Name: $companyName"
            if (industry != null && industry.trim().isNotBlank()) {
                tvIndustry.text = "Industry: $industry"
            }
        }
    }
}