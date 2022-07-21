package com.miniproject.soi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miniproject.soi.R
import com.miniproject.soi.data.History
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(list: List<History>): RecyclerView.Adapter<historyViewHolder>() {

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
        var d: Date? = null
        val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy")
        val temp = list.getDate()
        try {
            d= formatter.parse(temp)!!
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val formatDate = SimpleDateFormat("MM-dd-yyyy").format(d!!)
        val formatTime = SimpleDateFormat("HH:mm:ss").format(d)
        date.text = formatDate+" at "+formatTime
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