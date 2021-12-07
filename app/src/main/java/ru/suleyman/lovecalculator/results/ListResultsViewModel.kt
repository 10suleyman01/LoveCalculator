package ru.suleyman.lovecalculator.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import ru.suleyman.lovecalculator.LoveCalcViewModel
import ru.suleyman.lovecalculator.LoveResultModel
import ru.suleyman.lovecalculator.database.LoveListDao
import javax.inject.Inject

@HiltViewModel
class ListResultsViewModel @Inject constructor(private val loveListDao: LoveListDao) : ViewModel() {

    private var _state: MutableStateFlow<LoveResultsState> = MutableStateFlow(LoveResultsState.None)
    val state = _state.asStateFlow()

    fun getAllList() = viewModelScope.launch {
        _state.value = LoveResultsState.Results(loveListDao.getAllLoveHistory())
    }

    sealed class LoveResultsState {
        object None: LoveResultsState()
        data class Results(val list: List<LoveResultModel>): LoveResultsState()
    }
}