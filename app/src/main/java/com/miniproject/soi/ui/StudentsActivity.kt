package com.miniproject.soi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.miniproject.soi.R

class StudentsActivity : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    var students: List<String> = emptyList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: studentsAdapter
    lateinit var clickListener: studentsAdapter.ClickListener
    lateinit var backBtn: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students)
        databaseReference = Firebase.database.reference

        recyclerView = findViewById(R.id.rvStudents)

        backBtn = findViewById(R.id.imageViewHistory)

        backBtn.setOnClickListener {
            finish()
        }

        var users: List<String> = emptyList()

        databaseReference.child("Users").get().addOnSuccessListener {
            for (i in it.children) {
                users = users + i.key!!
            }
            Log.v("TAG!", users.toString())
        }.addOnCompleteListener {
            getStudents(users);
        }

        clickListener = object : studentsAdapter.ClickListener {
            override fun onClick(position: Int, view: View) {
                val uid = adapter.getStudentAtPosition(position)!!
                val intent = Intent(this@StudentsActivity, StudentHistoryActivity::class.java)
                intent.putExtra("STUDENT_UID", uid)
                startActivity(intent)
            }

        }
    }

    fun getStudents(users: List<String>) {
        for(i in users) {
            databaseReference.child("Users").child(i).child("Role").get().addOnSuccessListener {
                if (it.value.toString()=="student") {
                    students= students + i
                }
            }.addOnCompleteListener {
                recyclerView.setHasFixedSize(true)
                val layoutManager = LinearLayoutManager(this)
                recyclerView.layoutManager = layoutManager
                adapter = studentsAdapter(students)
                adapter.setItemClickListener(clickListener)
                recyclerView.adapter = adapter
            }
        }
    }
}