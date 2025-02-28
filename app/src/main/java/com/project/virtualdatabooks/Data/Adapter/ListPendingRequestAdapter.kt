package com.project.virtualdatabooks.Data.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.virtualdatabooks.Data.Response.AdminGetPendingResponse
import com.project.virtualdatabooks.Data.Response.PendingDataDiri
import com.project.virtualdatabooks.Data.Response.PendingDataItem
import com.project.virtualdatabooks.R

class ListPendingRequestAdapter(
    private val context: Context,
    private val data: List<PendingDataItem>
): RecyclerView.Adapter<ListPendingRequestAdapter.viewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListPendingRequestAdapter.viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_admin_pending_request, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: ListPendingRequestAdapter.viewHolder, position: Int) {
        val currentItem = data[position]

        holder.cardName.text = currentItem.dataDiri?.namaLengkap

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class viewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        val cardName: TextView = itemView.findViewById(R.id.studentNamePending)
    }



}