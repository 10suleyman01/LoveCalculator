package ru.suleyman.lovecalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.suleyman.lovecalculator.database.LoveListDao
import javax.inject.Inject

@HiltViewModel
class LoveCalcViewModel @Inject constructor(private val loveListDao: LoveListDao) : ViewModel() {

    private var _state: MutableStateFlow<LoveCalculatorState> = MutableStateFlow(LoveCalculatorState.None)
    val state = _state.asStateFlow()

    @DelicateCoroutinesApi
    fun getPercentage(sname: String, fname: String) = viewModelScope.launch {

        GlobalScope.launch(IO) {
            _state.value = LoveCalculatorState.Loading(true)
            val response = LoveCalculatorApi.getPercentage(sname, fname)
            withContext(Main) {
                response?.let { insert(it) }
                _state.value = LoveCalculatorState.Response(response)
                _state.value = LoveCalculatorState.Loading(false)
            }
        }
    }

    private fun insert(loveResultModel: LoveResultModel) = viewModelScope.launch {
        loveListDao.insert(loveResultModel)
    }

    sealed class LoveCalculatorState {
        object None : LoveCalculatorState()
        data class Loading(val loading: Boolean) : LoveCalculatorState()
        data class Response(val response: LoveResultModel?): LoveCalculatorState()
    }

}