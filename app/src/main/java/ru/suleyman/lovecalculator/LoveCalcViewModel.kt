package ru.suleyman.lovecalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoveCalcViewModel : ViewModel() {

    private var _state: MutableStateFlow<LoveCalculatorState> = MutableStateFlow(LoveCalculatorState.None)
    val state = _state.asStateFlow()

    fun getPercentage(sname: String, fname: String) = viewModelScope.launch {

        GlobalScope.launch(IO) {
            _state.value = LoveCalculatorState.Loading(true)
            val response = LoveCalculatorApi.getPercentage(sname, fname)
            withContext(Main) {
                _state.value = LoveCalculatorState.Response(response)
                _state.value = LoveCalculatorState.Loading(false)
            }
        }



    }

    sealed class LoveCalculatorState {
        object None : LoveCalculatorState()
        data class Loading(val loading: Boolean) : LoveCalculatorState()
        data class Response(val response: LoveResultModel?): LoveCalculatorState()
    }

}