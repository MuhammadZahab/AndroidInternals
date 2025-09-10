package com.zahab.androidinternals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CounterViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val counter = savedStateHandle.getStateFlow("counter", 0)
    val greeting = savedStateHandle.get<String>("greetings")

    init {
        println("The greeting is: $greeting")
    }

    fun increment() {

        savedStateHandle.update<Int>("counter") { it ->
            it + 1
        }

    }

    override fun onCleared() {
        super.onCleared()
        println("Counter view model cleared")
    }

    inline fun <T> SavedStateHandle.update(key: String, update: (T) -> T) {

        val current = get<T>(key)
        val updated = update(current ?: return)  //return from here if current is null

        set(key, updated)
    }

}