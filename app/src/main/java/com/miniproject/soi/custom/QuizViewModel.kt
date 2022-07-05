package com.miniproject.soi.custom

import android.app.Application
import androidx.arch.core.util.Function
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.miniproject.soi.data.State
import com.miniproject.soi.data.StateRepository

class QuizViewModel(application: Application): AndroidViewModel(application) {

    lateinit var states: LiveData<List<State>>
    private val quizRepository = StateRepository.getRepository(application)!!
    val count = MutableLiveData<Int>()
    val increment = MutableLiveData<Int>()
    var i =0
    val trigger: customLiveData

    init {
        count.value = 4
        increment.value = i
        trigger = customLiveData(count, increment)
        loadGame()
    }

    private fun loadGame(){
        states = Transformations.switchMap(trigger, Function {
            quizRepository.getQuizStates(it.first)
        })
    }

    fun refreshGame(){
        i++
        increment.postValue(i)
        loadGame()
    }
}