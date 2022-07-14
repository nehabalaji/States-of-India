package com.miniproject.soi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.miniproject.soi.R
import com.miniproject.soi.data.History

class HistoryAdapter(list: List<History>): RecyclerView.Adapter<historyViewHolder>() {

    val mAuth = FirebaseAuth.getInstance()
    val databaseReference = Firebase.database.reference
    private var myList: List<History> = emptyList()

    init {
        this.myList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): historyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.item_history, parent, false)
        val viewHolder = historyViewHolder(listItem)
        return viewHolder
    }

    override fun onBindViewHolder(holder: historyViewHolder, position: Int) {
        var list = myList[position]
        val correct = holder.count
        val points = holder.points
        val date = holder.date

        correct.text = list.getCorrect()
        points.text = list.getPoints()
        date.text = list.getDate()
    }

    override fun getItemCount(): Int {
        return myList.size
    }
}

class historyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var count: TextView
    var points: TextView
    var date: TextView

    init {
        count = itemView.findViewById(R.id.correctAnswerTV)
        points = itemView.findViewById(R.id.earnedTV)
        date = itemView.findViewById(R.id.dateHistoryTV)
    }
}