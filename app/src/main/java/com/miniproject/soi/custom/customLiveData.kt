package com.miniproject.soi.custom

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class customLiveData(count: MutableLiveData<Int>, increment: MutableLiveData<Int>) :
    MediatorLiveData<Pair<Int, Int>>() {

    init {
        addSource(count){
            if (it!=null)
                value = Pair(it, increment.value!!)
        }
        addSource(increment){
            if (it!=null){
                value = Pair(count.value!!, it)
            }
        }
    }
}