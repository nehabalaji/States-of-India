package com.miniproject.soi.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.miniproject.soi.R
import com.miniproject.soi.data.History

class StudentHistoryActivity: AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var recyclerView: RecyclerView
    var list: List<String> = emptyList()
    var historyList: List<History> = emptyList()
    lateinit var uid: String
    lateinit var backBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_student)

        databaseReference = Firebase.database.reference

        backBtn = findViewById(R.id.imageViewHistory)

        backBtn.setOnClickListener {
            finish()
        }

        uid = intent.extras?.getString("STUDENT_UID")!!
        recyclerView = findViewById(R.id.rvHistory)
        databaseReference.child("Users").child(uid).child("History").get().addOnSuccessListener {
            for (i in it.children) {
                list = list + i.key!!
            }
        }.addOnCompleteListener {
            getHistory(list);
        }
    }
    fun getHistory(list: List<String>) {
        for (i in list) {
            databaseReference.child("Users").child(uid).child("History").child(i).get().addOnSuccessListener {
                val c = it.child("correct").value.toString()
                val points = it.child("points").value.toString()
                val history = History(i, c, points)
                historyList = historyList + history
            }.addOnCompleteListener {
                recyclerView.setHasFixedSize(true)
                val layoutManager = LinearLayoutManager(this)
                recyclerView.layoutManager = layoutManager
                val adapter = HistoryAdapter(historyList)
                recyclerView.adapter = adapter
            }
        }
    }
}