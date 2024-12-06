package com.test.vodafone.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.vodafone.R
import com.test.vodafone.ui.viewmodel.OffersViewModel

class OffersFragment : Fragment(R.layout.fragment_offers) {

    private val offersViewModel: OffersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton = view.findViewById<View>(R.id.toolbar_log_out)
        val test = view.findViewById<View>(R.id.test)

        logoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_offers_fragment_to_login_fragment)
        }

        test.setOnClickListener {
            findNavController().navigate(R.id.action_offers_fragment_to_offers_datalis_fragment)
        }

        offersViewModel.offers.observe(viewLifecycleOwner, Observer { offers ->
            // Itt kezelhetjük az ajánlatok listáját
            // Például a RecyclerView-t frissíthetjük
        })


    }
}