package com.project.virtualdatabooks.Data.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.virtualdatabooks.Data.DataClass.ItemSearchItem
import com.project.virtualdatabooks.R

class ListSearchResultAdapter(
    private val context: Context,
    private val data: List<ItemSearchItem>,
    private val onExportClick: (Int) -> Unit
): RecyclerView.Adapter<ListSearchResultAdapter.viewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListSearchResultAdapter.viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_detailer_major_data, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: ListSearchResultAdapter.viewHolder, position: Int) {
        val currentItem = data[position]

        holder.rollNumber.text = (position + 1).toString()
        holder.fullName.text = currentItem.nama

        holder.btnExportReport.setOnClickListener {
            currentItem.id?.let { userId -> onExportClick(userId) }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rollNumber: TextView = itemView.findViewById(R.id.tv_numDetailedMajor)
        val fullName: TextView = itemView.findViewById(R.id.tv_nameDetailedMajor)
        val btnViewReport: Button = itemView.findViewById(R.id.btn_seeRaporDetailedMajor)
        val btnExportReport: Button = itemView.findViewById(R.id.btn_downloadRaporDetailedMajor)
    }

}