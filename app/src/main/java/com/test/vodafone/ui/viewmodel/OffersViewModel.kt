package com.test.vodafone.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.test.vodafone.server.ApiServerI
import com.test.vodafone.server.OffersData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val server: ApiServerI
): ViewModel() {

    private val _emptyOffersList = emptyList<OffersData>()
    private val _offersList = MutableStateFlow(_emptyOffersList)
    val offers: LiveData<List<OffersData>> = _offersList.asLiveData()
    private fun updateOffersData(list: List<OffersData>) = _offersList.update { list }

    init {
        downloadOffers()
    }

    fun downloadOffers(){
        viewModelScope.launch{
            val offers = server.apiRepository.getOffers()
            updateOffersData(_emptyOffersList)
            updateOffersData(offers)
        }
    }

}