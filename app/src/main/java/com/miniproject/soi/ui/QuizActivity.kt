package com.miniproject.soi.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.miniproject.soi.R
import com.miniproject.soi.custom.QuizView
import com.miniproject.soi.custom.QuizViewModel
import com.miniproject.soi.custom.QuizViewModelFactory

class QuizActivity : AppCompatActivity() {
    private var quizView: QuizView? = null
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var optionsPreference: SharedPreferences
    private lateinit var noOfOptions: String
    var noOfQuestions=0;
    var noOfCorrectAnswers =0;
    var noOfWrongAnswers=0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        optionsPreference = PreferenceManager.getDefaultSharedPreferences(this)
        optionsPreference.registerOnSharedPreferenceChangeListener(listener)

        noOfOptions = optionsPreference.getString("no_of_options", "four")!!
        var value = 4
        if (noOfOptions.equals("four")){
            value = 4
        }else{
            value = 3
        }

        quizViewModel = ViewModelProvider(
            this,
            QuizViewModelFactory(this.application)
        )[QuizViewModel::class.java]
        quizView = findViewById(R.id.quizView)

        quizViewModel.states.observe(this, Observer {
            if (it.size==4 || it.size==3){
                quizView?.setData(it, value)
            }else{
                Toast.makeText(this,"Add more states",Toast.LENGTH_SHORT).show()
            }
        })

        val optionsClickListener = object :  QuizView.OptionsClickListener {
            override fun onClick(result: Boolean) {
                updateResult(result)
            }
        }

        quizView?.setOnOptionsClickListener(optionsClickListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater =  menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        when(id){
            R.id.list -> {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateResult(result: Boolean){
        noOfQuestions++;
        if(result){
//            Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show()
            noOfCorrectAnswers++;
        }
        else{
//            Toast.makeText(this,"Wrong",Toast.LENGTH_SHORT).show()
            noOfWrongAnswers++;
        }
        if (noOfQuestions<10) {
            quizViewModel.refreshGame()
            quizView?.reset()
        }
        else {
            noOfQuestions=0;
            val intent = Intent(this, ResultsActivity::class.java)
            intent.putExtra("CORRECT_ANSWERS", noOfCorrectAnswers);
            intent.putExtra("WRONG_ANSWERS", noOfWrongAnswers);
            startActivity(intent);
            finishAffinity();
        }
    }

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, s ->

        if (s.equals("no_of_options")){
            val string = optionsPreference.getString(s, "four")
            var value = 4
            if (s.equals("four")){
                value = 4
            }else if (s.equals("three")){
                value = 3
            }
            quizViewModel.count.postValue(value)
            quizViewModel.refreshGame()
            quizView?.reset()
        }
    }

    override fun onStop() {
        super.onStop()
        optionsPreference.unregisterOnSharedPreferenceChangeListener(listener)
    }

    override fun onResume() {
        super.onResume()
        if (!noOfOptions.equals(optionsPreference.getString("no_of_options", "four"))){
            quizViewModel.refreshGame()
            quizView?.reset()
            recreate()
        }
    }
}