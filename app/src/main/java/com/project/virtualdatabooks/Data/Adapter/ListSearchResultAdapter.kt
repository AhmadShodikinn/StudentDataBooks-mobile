package com.project.virtualdatabooks.Data.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.project.virtualdatabooks.Data.DataClass.ItemSearchItem
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.UI.ERaportAdminFragment

class ListSearchResultAdapter(
    private val context: Context,
    private val data: List<ItemSearchItem>,
    private val onExportPdfClick: (Int) -> Unit,
    private val onExportExcelClick: (Int) -> Unit
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
            holder.cardDownloadOption.visibility = View.VISIBLE
            holder.btnViewReport.visibility = View.GONE
        }

        holder.downloadPdf.setOnClickListener {
            currentItem.id?.let { userId -> onExportPdfClick(userId) }
        }

        holder.downloadExcel.setOnClickListener {
            currentItem.id?.let { userId -> onExportExcelClick(userId) }
        }

        holder.btnViewReport.setOnClickListener {
            val bundle = Bundle()
            currentItem.id?.let { id -> bundle.putInt("USER_ID", id) }
            bundle.putString("NAMA", currentItem.nama)
            bundle.putString("NISN", currentItem.nisn)
            bundle.putString("JURUSAN", currentItem.jurusan)

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

            val adminViewRaport = ERaportAdminFragment()
            adminViewRaport.arguments = bundle

            transaction.replace(R.id.fragment_container, adminViewRaport)
            transaction.addToBackStack(null)
            transaction.commit()
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

        val cardDownloadOption: LinearLayout = itemView.findViewById(R.id.cardDownloadOption)

        val downloadPdf: TextView = itemView.findViewById(R.id.downloadPdf)
        val downloadExcel: TextView = itemView.findViewById(R.id.downloadExcel)
    }

}