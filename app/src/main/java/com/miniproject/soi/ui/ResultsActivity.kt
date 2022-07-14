package com.miniproject.soi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.miniproject.soi.R
import java.text.SimpleDateFormat
import java.util.*

class ResultsActivity : AppCompatActivity() {

    lateinit var correctAnswers: TextView
    lateinit var wrongAnswers: TextView
    lateinit var earnedPoints: TextView
    lateinit var date: TextView
    lateinit var startAgain: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        correctAnswers = findViewById(R.id.correctTV);
        wrongAnswers = findViewById(R.id.incorrectTV);
        earnedPoints = findViewById(R.id.earnedPointsTV);
        date = findViewById(R.id.dateTV);
        startAgain = findViewById(R.id.startAgainBtn);

        val correct = intent.extras?.getInt("CORRECT_ANSWERS")!!
        val wrong = intent.extras?.getInt("WRONG_ANSWERS")!!
        correctAnswers.text = ""+correct
        wrongAnswers.text = ""+wrong
        val score = (5*correct) - (2*wrong)
        earnedPoints.text=""+score
        val d = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        val quizDate = df.format(d);
        date.text = quizDate

        startAgain.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent);
            finish()
        }
    }
}