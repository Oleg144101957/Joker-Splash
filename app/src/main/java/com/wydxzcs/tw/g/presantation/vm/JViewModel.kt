package com.wydxzcs.tw.g.presantation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wydxzcs.tw.g.R
import com.wydxzcs.tw.g.domain.models.JokerState
import kotlin.random.Random

class JViewModel : ViewModel() {

    private val stateGameMutable = MutableLiveData<JokerState>()
    val stateGame: LiveData<JokerState> = stateGameMutable

    init {
        stateGameMutable.value = JokerState(0)
    }

    fun sendData(jokerGameEvent: JokerGameEvent){
        when (jokerGameEvent){
            is IncreasePoints -> {
                increasePoints()
            }
        }
    }

    private fun increasePoints() {
        val currentPoints = stateGameMutable.value?.points
        if (currentPoints != null) {
            stateGameMutable.value = stateGameMutable.value?.copy(points = currentPoints + 1)
        }
    }
}