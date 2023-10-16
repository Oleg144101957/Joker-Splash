package com.wydxzcs.tw.g.presantation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wydxzcs.tw.g.domain.models.GameStatus
import com.wydxzcs.tw.g.domain.models.JokerState

class JViewModel : ViewModel() {

    private val stateGameMutable = MutableLiveData<JokerState>()
    val stateGame: LiveData<JokerState> = stateGameMutable

    init {
        stateGameMutable.value = JokerState(0, gameStatus = GameStatus.PLAY_GAME)
    }

    fun sendData(jokerGameEvent: JokerGameEvent){
        when (jokerGameEvent){
            is IncreasePoints -> {
                increasePoints()
            }

            is DecreasePoints -> {
                decreaseScores()
            }
        }
    }

    private fun increasePoints() {
        val currentPoints = stateGameMutable.value?.points
        val newPoints = currentPoints?.plus(1) ?: 0
        if (currentPoints != null) {
            stateGameMutable.value = stateGameMutable.value?.copy(points = newPoints)
        }
    }

    private fun decreaseScores(){
        val currentPoints = stateGameMutable.value?.points
        val newPoints = currentPoints?.minus(1) ?: 0

        if (newPoints<0){
            stateGameMutable.value = stateGameMutable.value?.copy(
                points = 0,
                gameStatus = GameStatus.GAME_OVER
            )
        } else {
            stateGameMutable.value = stateGameMutable.value?.copy(points = newPoints)
        }
    }
}