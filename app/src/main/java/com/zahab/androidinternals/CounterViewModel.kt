package com.zahab.androidinternals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {

     var counter by mutableIntStateOf(0)
    private set


    fun increment(){
        counter++
    }

    override fun onCleared() {
        super.onCleared()
        println("Counter view model cleared")
    }

}