package com.test.vodafone.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.test.vodafone.R
import com.test.vodafone.server.OffersData

class OffersListAdapter(
    private val items: List<OffersData>,
    private val onItemClick: (OffersData) -> Unit
) : RecyclerView.Adapter<OffersListAdapter.ListViewHolder>() {

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.title)
        val descriptionText: TextView = view.findViewById(R.id.description)
        val offersCard: CardView = view.findViewById(R.id.offers_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]
        holder.titleText.text = item.name
        holder.descriptionText.text = item.shortDescription
        holder.offersCard.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = items.size
}