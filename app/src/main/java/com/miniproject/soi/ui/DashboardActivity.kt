package com.miniproject.soi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.miniproject.soi.R

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val mAuth = FirebaseAuth.getInstance()
        val databaseReference = Firebase.database.reference

        val teachersView = findViewById<ConstraintLayout>(R.id.teachers_view)
        val studentsView = findViewById<ConstraintLayout>(R.id.students_view)

        val progressIndicator = findViewById<ProgressBar>(R.id.progressBar)
        progressIndicator.visibility = View.VISIBLE
        databaseReference.child("Users").child(mAuth.currentUser?.uid.toString()).child("Role").get().addOnSuccessListener {
            if (it.value.toString()=="teacher") {
                Log.v("TAG", it.value.toString())
                teachersView.visibility = View.VISIBLE
                studentsView.visibility = View.GONE
                progressIndicator.visibility = View.GONE
            }
            else {
                Log.v("TAG", it.value.toString())
                studentsView.visibility = View.VISIBLE
                teachersView.visibility = View.GONE
                progressIndicator.visibility = View.GONE
            }
        }

        val startQuiz = findViewById<CardView>(R.id.cvStartQuiz);
        val rules = findViewById<CardView>(R.id.cvRule);
        val history = findViewById<CardView>(R.id.cvHistory);
        val logout = findViewById<CardView>(R.id.cvLogout);
        val students = findViewById<CardView>(R.id.cvStudents);
        val logoutTeachers = findViewById<CardView>(R.id.cvLogoutTeachers);

        startQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java);
            startActivity(intent)
        }

        rules.setOnClickListener {
            val intent = Intent(this, RulesActivity::class.java);
            startActivity(intent)
        }

        history.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java);
            startActivity(intent)
        }

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent)
            finishAffinity()
        }

        logoutTeachers.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent)
            finishAffinity()
        }

        students.setOnClickListener {
            val intent = Intent(this, StudentsActivity::class.java);
            startActivity(intent)
        }
    }
}