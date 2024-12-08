package com.test.vodafone.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.test.vodafone.R
import com.test.vodafone.ui.viewmodel.OfferDatalisViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferDatalisFragment : Fragment(R.layout.fragment_offer_datalis) {

    private val viewModel: OfferDatalisViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val offerId = arguments?.getString("id")
        viewModel.downloadOffers(offerId?:"")
        val backButton = view.findViewById<View>(R.id.toolbar_back)
        val toolbarTitle = view.findViewById<TextView>(R.id.toolbar_title)
        val title = view.findViewById<TextView>(R.id.title)
        val shortDescription = view.findViewById<TextView>(R.id.short_description)
        val description = view.findViewById<TextView>(R.id.description)

        viewModel.detail.observe(viewLifecycleOwner, Observer { detail ->
            if (detail != null) {
                toolbarTitle.text = detail.name
                title.text = detail.name
                shortDescription.text = detail.shortDescription
                description.text = detail.description
            }
        })

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }




    }
}