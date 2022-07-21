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

class studentsAdapter(list: List<String>): RecyclerView.Adapter<studentViewHolder>() {

    private lateinit var clickListener: ClickListener

    val mAuth = FirebaseAuth.getInstance()
    val databaseReference = Firebase.database.reference
    private var myList: List<String> = emptyList()

    init {
        this.myList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): studentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.item_students, parent, false)
        val viewHolder = studentViewHolder(listItem)
        return viewHolder
    }

    override fun onBindViewHolder(holder: studentViewHolder, position: Int) {
        val uid = myList[position]
        val studentId = holder.student
        holder.itemView.setOnClickListener {
            clickListener.onClick(position, it)
        }
        databaseReference.child("Users").child(uid.toString()).child("Name").get().addOnSuccessListener {
            studentId.text = it.value.toString()
        }
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    interface ClickListener {
        fun onClick(position: Int, view: View)
    }

    fun setItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    fun getStudentAtPosition(position: Int): String? {
        return myList[position]
    }
}

class studentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var student: TextView

    init {
        student = itemView.findViewById(R.id.studentID)
    }
}

