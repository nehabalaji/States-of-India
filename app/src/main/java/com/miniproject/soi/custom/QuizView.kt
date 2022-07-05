package com.miniproject.soi.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.miniproject.soi.data.State
import kotlin.random.Random

class QuizView(c: Context, attributeSet: AttributeSet) : LinearLayout(c, attributeSet) {

    private lateinit var options: RadioGroup
    private var correctOptionID: Int? = null
    private lateinit var optionsClickListener: OptionsClickListener
    private lateinit var radioButtons: Array<RadioButton>

    init {
        initRadios()
    }

    interface OptionsClickListener{
        fun onClick(result: Boolean)
    }

    fun setOnOptionsClickListener(optionsClickListener: OptionsClickListener){
        this.optionsClickListener = optionsClickListener
    }

    private fun initRadios(){
        options = RadioGroup(context)
        options.id = View.generateViewId()
    }

    fun setData(quiz: List<State>, value: Int){
        val random = Random(System.currentTimeMillis())

        val correctOption = random.nextInt(value)
        val correctState = quiz[correctOption]

        val questionTV: TextView = TextView(context)
        val question = "What is the capital of state ${correctState.stateName}"

        questionTV.text = question
        questionTV.setPadding(20, 20, 20, 20)
        questionTV.setTextColor(resources.getColor(android.R.color.black))
        questionTV.textSize = 24F

        this.addView(questionTV)
        this.addView(options)

        if(value==3){
            radioButtons = arrayOf(
                RadioButton(context),
                RadioButton(context),
                RadioButton(context)
            )
        }else if(value==4){
            radioButtons = arrayOf(
                RadioButton(context),
                RadioButton(context),
                RadioButton(context),
                RadioButton(context)
            )
        }




        var i=0
        var j=0
        while (i<value && j<value){
            if (i==correctOption){
                radioButtons[i].id = View.generateViewId()
                radioButtons[i].text = correctState.capitalName
                correctOptionID = radioButtons[i].id
                options.addView(radioButtons[i])
            }else{
                radioButtons[i].id = View.generateViewId()
                radioButtons[i].text = quiz[j].capitalName
                options.addView(radioButtons[i])
            }
            initListeners()
            i++
            j++
        }
    }

    private fun initListeners(){
        options.setOnCheckedChangeListener { _, i ->
            if (i==correctOptionID){
                optionsClickListener.onClick(true)
            }else{
                optionsClickListener.onClick(false)
            }
        }
    }

    fun reset(){
        options.removeAllViews()
        this.removeAllViews()
    }
}