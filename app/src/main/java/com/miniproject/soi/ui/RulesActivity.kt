package com.miniproject.soi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.miniproject.soi.R

class RulesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)

        var backBtn = findViewById<ImageView>(R.id.imageRule)
        backBtn.setOnClickListener {
            finish()
        }
    }
}