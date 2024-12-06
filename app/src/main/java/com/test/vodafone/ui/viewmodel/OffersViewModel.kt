package com.test.vodafone.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(

): ViewModel() {
    val offers = repository.getOffers()
}