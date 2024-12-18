package com.test.vodafone.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.test.vodafone.server.ApiServerI
import com.test.vodafone.server.DetailsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferDatalisViewModel @Inject constructor(
    private val server: ApiServerI
): ViewModel() {

    private val _detailsData = MutableStateFlow<DetailsData?>(null)
    val detail: LiveData<DetailsData?> = _detailsData.asLiveData()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage.asLiveData()

    fun downloadOffers(id: String){
        if(id!="") {
            viewModelScope.launch {
                try {
                    _detailsData.value = server.apiRepository.getDetail(id)[0]
                }catch (e: Exception){
                    _errorMessage.value = e.message
                }
            }
        }
    }

}