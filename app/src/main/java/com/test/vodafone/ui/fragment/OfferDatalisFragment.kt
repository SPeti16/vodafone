package com.test.vodafone.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.test.vodafone.R
import com.test.vodafone.ui.viewmodel.OfferDatalisViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfferDatalisFragment : Fragment(R.layout.fragment_offer_datalis) {

    private val viewModel: OfferDatalisViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val offerId = arguments?.getString("id")
        val isSpecial = arguments?.getBoolean("isSpecial")?:false
        viewModel.downloadOffers(offerId?:"")
        val backButton = view.findViewById<View>(R.id.toolbar_back)
        val toolbarTitle = view.findViewById<TextView>(R.id.toolbar_title)
        val title = view.findViewById<TextView>(R.id.title)
        val shortDescription = view.findViewById<TextView>(R.id.short_description)
        val description = view.findViewById<TextView>(R.id.description)

        viewModel.detail.observe(viewLifecycleOwner)  { detail ->
            if (detail != null) {
                toolbarTitle.text = detail.name
                title.text = detail.name
                shortDescription.text = detail.shortDescription
                description.text = detail.description
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){ e ->
            if(e != null) {
                Toast.makeText(requireContext(), R.string.details_offers_error, Toast.LENGTH_LONG).show()
            }
        }

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        if(isSpecial){
            val imageView = view.findViewById<ImageView>(R.id.special_offers_logo)
            val imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcROuCNDDZTHJOt4xYcG10xQozTEO9teoG9cc9nMNSPNQjeQ-IfaaWgdAw6fwgLsgmZeZe8&usqp=CAU"
            Picasso.get()
                .load(imageUrl)
                .into(imageView)
            imageView.visibility = View.VISIBLE
        }




    }
}