package com.test.vodafone.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.vodafone.R

class OfferDatalisFragment : Fragment(R.layout.fragment_offer_datalis) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<View>(R.id.toolbar_back)

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }




    }
}