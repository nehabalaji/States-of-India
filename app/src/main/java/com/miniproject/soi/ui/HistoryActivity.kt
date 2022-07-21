package com.miniproject.soi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.miniproject.soi.R
import com.miniproject.soi.data.History

class HistoryActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    val mAuth = FirebaseAuth.getInstance()
    lateinit var recyclerView: RecyclerView
    var list: List<String> = emptyList()
    var historyList: List<History> = emptyList()
    lateinit var backBtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        databaseReference = Firebase.database.reference

        recyclerView = findViewById(R.id.rvHistory)
        backBtn = findViewById(R.id.imageViewHistory)

        backBtn.setOnClickListener {
            finish()
        }

        databaseReference.child("Users").child(mAuth.currentUser?.uid.toString()).child("History").get().addOnSuccessListener {
            for (i in it.children) {
                list = list + i.key!!
            }
        }.addOnCompleteListener {
            getHistory(list);
        }
    }

    fun getHistory(list: List<String>) {
        for (i in list) {
            databaseReference.child("Users").child(mAuth.currentUser?.uid.toString()).child("History").child(i).get().addOnSuccessListener {
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