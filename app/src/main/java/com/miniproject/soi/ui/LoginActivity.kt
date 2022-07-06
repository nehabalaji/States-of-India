package com.miniproject.soi.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.miniproject.soi.R


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = Firebase.auth
        val emailET = findViewById<EditText>(R.id.etUsername);
        val passwordEt = findViewById<EditText>(R.id.etPassword);
        val loginBtn = findViewById<Button>(R.id.btnLogin);

        loginBtn.setOnClickListener {
            val email = emailET.text.toString()
            val password = passwordEt.text.toString()
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, QuizActivity::class.java)
                    startActivity(intent);
                    finish()
                }
                else {
                    Log.v("SIGN IN", it.exception.toString())
                    Toast.makeText(this, "Check credentials!"+it.exception, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = mAuth.currentUser;
        if(currentUser!=null) {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}