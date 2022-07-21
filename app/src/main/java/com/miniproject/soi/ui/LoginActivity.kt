package com.miniproject.soi.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.miniproject.soi.R

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login)

        mAuth = Firebase.auth
        val emailET = findViewById<EditText>(R.id.etUsername);
        val passwordEt = findViewById<EditText>(R.id.etPassword);
        val loginBtn = findViewById<Button>(R.id.btnLogin);

        database = Firebase.database.reference

        loginBtn.setOnClickListener {
            val email = emailET.text.toString()
            val password = passwordEt.text.toString()
            if(email.isEmpty() || email.isNullOrBlank() || password.isEmpty() || password.isNullOrBlank()) {
                Toast.makeText(this, "Enter a valid Username/Password", Toast.LENGTH_SHORT).show()
            }
            else {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent);
                        finish()
                    }
                    else {
                        Log.v("SIGN IN", it.exception.toString())
                        Toast.makeText(this, "Check credentials!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = mAuth.currentUser;
        if(currentUser!=null) {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}