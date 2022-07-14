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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.miniproject.soi.R

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = Firebase.auth
        val emailET = findViewById<EditText>(R.id.etUsername);
        val passwordEt = findViewById<EditText>(R.id.etPassword);
        val loginBtn = findViewById<Button>(R.id.btnLogin);

        database = Firebase.database.reference

        loginBtn.setOnClickListener {
            val email = emailET.text.toString()
            val password = passwordEt.text.toString()
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    var uid = it.result.user?.uid!!
                    database.child("Users").child(uid).child("role").get().addOnSuccessListener {
                        if (it.value?.toString().equals("student")!!) {
                            val intent = Intent(this, QuizActivity::class.java)
                            startActivity(intent);
                            finish()
                        }
                        else {
                            val intent = Intent(this, TeachersActivity::class.java)
                            startActivity(intent);
                            finish()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this, "Error occurred!", Toast.LENGTH_SHORT).show()
                    }
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
            database.child("Users").child(currentUser.uid).child("role").get().addOnSuccessListener {
                if (it.value?.toString().equals("student")!!) {
                    val intent = Intent(this, TeachersActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    val intent = Intent(this, QuizActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error occurred!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}