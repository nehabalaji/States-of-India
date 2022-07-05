package com.miniproject.soi.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StateAndCapital")
data class State(
    @ColumnInfo(name = "StateName")
    var stateName: String,

    @ColumnInfo(name = "CapitalName")
    var capitalName: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Long = 0L
)
