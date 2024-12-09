package com.test.vodafone.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.test.vodafone.server.ApiServerI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val server: ApiServerI
): ViewModel() {

    private val _detailsData = MutableStateFlow("")
    val detail: LiveData<String> = _detailsData.asLiveData()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage.asLiveData()

    fun login(username: String, password: String){
        viewModelScope.launch{
            try {
                if (server.apiRepository.getLogin().any { it.username == username && it.password == password }) {
                    _detailsData.value = username
                } else {
                    _detailsData.value = "fail"
                }
            }catch (e: Exception){
                _detailsData.value = "fail"
                _errorMessage.value = e.message
            }
        }
    }

}