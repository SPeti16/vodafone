package com.test.vodafone.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.test.vodafone.R
import com.test.vodafone.server.OffersData
import com.test.vodafone.ui.adapter.OffersListAdapter
import com.test.vodafone.ui.viewmodel.OffersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OffersFragment : Fragment(R.layout.fragment_offers) {

    private val viewModel: OffersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = arguments?.getString("user")

        val logoutButton = view.findViewById<View>(R.id.toolbar_log_out)
        val offersRecyclerView = view.findViewById<RecyclerView>(R.id.offers_list)
        val specialOffersRecyclerView = view.findViewById<RecyclerView>(R.id.special_offers_list)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        val specialOffersTitle = view.findViewById<TextView>(R.id.special_offers_title)
        swipeRefreshLayout.isRefreshing = true

        logoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_offers_fragment_to_login_fragment)
        }

        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            if (offers.isNotEmpty()) {
                makeList(offers, offersRecyclerView, false)
                if(user != "guest"){
                    makeList(offers.filter {it.id!=null && it.rank!=null}, specialOffersRecyclerView, true)
                }
                swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){ e ->
            if(e != null) {
                Toast.makeText(requireContext(), R.string.offers_error, Toast.LENGTH_LONG).show()
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.downloadOffers()
        }

        if(user == "guest"){
            specialOffersTitle.visibility = View.GONE
            specialOffersRecyclerView.visibility = View.GONE
        }
    }

    private fun makeList(offers: List<OffersData>, listView: RecyclerView, isSpecial: Boolean){
        val adapter = OffersListAdapter(offers.filter {it.isSpecial == isSpecial}.sortedBy {it.rank}){offer ->
            findNavController().navigate(
                R.id.action_offers_fragment_to_offers_datalis_fragment,
                Bundle().apply {
                    putString("id", offer.id)
                    putBoolean("isSpecial", isSpecial)
                }
            )
        }
        listView.layoutManager = LinearLayoutManager(requireContext())
        listView.adapter = adapter
    }
}