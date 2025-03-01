package com.project.virtualdatabooks.Data.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.project.virtualdatabooks.Data.Response.AdminGetPendingResponse
import com.project.virtualdatabooks.Data.Response.PendingDataDiri
import com.project.virtualdatabooks.Data.Response.PendingDataItem
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.UI.EditFormAdminFragment

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

        holder.buttonAction.setOnClickListener {
            val bundle = Bundle()
            currentItem.id?.let { id -> bundle.putInt("STUDENT_ID", id) }

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

            val editFormAdminFragment = EditFormAdminFragment()
            editFormAdminFragment.arguments = bundle

            transaction.replace(R.id.fragment_container, editFormAdminFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class viewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        val cardName: TextView = itemView.findViewById(R.id.studentNamePending)
        val buttonAction: Button = itemVIew.findViewById(R.id.confirmation_button)
    }



}