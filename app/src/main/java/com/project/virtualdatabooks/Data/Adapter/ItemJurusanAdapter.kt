package com.project.virtualdatabooks.Data.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.project.virtualdatabooks.Data.DataClass.ItemJurusanItem
import com.project.virtualdatabooks.R
import com.project.virtualdatabooks.UI.DetailedMajorStudentFragment

class ItemJurusanAdapter(
    private val context: Context,
    private val itemJurusan: List<ItemJurusanItem>
): RecyclerView.Adapter<ItemJurusanAdapter.viewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_jurusan, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder , position: Int) {
        val currentItem = itemJurusan[position]
        holder.namaJurusan.text = currentItem.nama

        when (currentItem.nama) {
            "Rekayasa Perangkat Lunak" -> holder.iconJurusan.setImageResource(R.mipmap.ic_rpl_foreground)
            "Desain Komunikasi Visual" -> holder.iconJurusan.setImageResource(R.mipmap.ic_dkv_foreground)
            "Audio Video" -> holder.iconJurusan.setImageResource(R.mipmap.ic_tav_foreground)
            "Broadcasting" -> holder.iconJurusan.setImageResource(R.mipmap.ic_broadcast_foreground)
            "Animasi" -> holder.iconJurusan.setImageResource(R.mipmap.ic_animasi_foreground)
            "Teknik Komunikasi Jaringan" -> holder.iconJurusan.setImageResource(R.mipmap.ic_tkj_foreground)
            "Elektronika Industri" -> holder.iconJurusan.setImageResource(R.mipmap.ic_tei_foreground)
            "Mekatronika" -> holder.iconJurusan.setImageResource(R.mipmap.ic_mekatro_foreground)
            else -> holder.iconJurusan.setImageResource(R.mipmap.ic_rpl_foreground)
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("POSITION", position + 1)

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

            val detailedFragment = DetailedMajorStudentFragment()
            detailedFragment.arguments = bundle

            transaction.replace(R.id.fragment_container, detailedFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun getItemCount(): Int {
        return itemJurusan.size
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconJurusan: ImageView = itemView.findViewById(R.id.tvLogoJurusan)
        val namaJurusan: TextView = itemView.findViewById(R.id.tvNamaJurusan)
    }
}