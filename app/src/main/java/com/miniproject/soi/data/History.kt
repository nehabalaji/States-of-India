package com.miniproject.soi.data

class History(date: String, correct: String, points: String) {

    var d: String = date
    var c: String = correct
    var p: String = points

    fun getDate(): String {
        return d
    }
    fun getCorrect(): String {
        return c
    }
    fun getPoints(): String {
        return p
    }
}