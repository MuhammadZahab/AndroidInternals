package com.zahab.androidinternals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CounterViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val counter = savedStateHandle.getStateFlow("counter",0)


    fun increment(){
        savedStateHandle.get<Int>("counter")?.let { counter->
            savedStateHandle["counter"] = counter + 1
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("Counter view model cleared")
    }

}