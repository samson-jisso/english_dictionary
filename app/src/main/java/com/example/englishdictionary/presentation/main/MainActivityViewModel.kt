package com.example.englishdictionary.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishdictionary.core.util.Resource
import com.example.englishdictionary.domain.use_case.GetWordInfo
import com.example.englishdictionary.presentation.WordInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject
constructor(
    private val getWordInfo: GetWordInfo
) : ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

    private  val _eventFlow  = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob:Job? = null

    fun onSearch(query:String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getWordInfo.invoke(query)
                .onEach {result ->
                    when(result) {
                        is Resource.Success ->  {
                            _state.value = _state.value.copy(
                                wordInfoState = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                wordInfoState = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                wordInfoState = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UIEvent.ShowSnackBar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }
                    }
                }.launchIn(this)
        }
    }


  sealed class UIEvent {
      data class ShowSnackBar(val message:String):UIEvent()
  }
}