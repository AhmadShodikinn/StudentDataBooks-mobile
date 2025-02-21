package com.project.virtualdatabooks.Data.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.virtualdatabooks.Data.DataClass.ItemAngkatanItem
import com.project.virtualdatabooks.R

class ItemAngkatanAdapter(
    private val context: Context,
    private val itemAngkatan: List<ItemAngkatanItem>
): RecyclerView.Adapter<ItemAngkatanAdapter.viewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_detailed_jurusan, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAngkatanAdapter.viewHolder, position: Int) {
        //ngide
        val angkatanIndex = position / 2
        val kelasIndex = position % 2 + 1

        val currentItem = itemAngkatan[angkatanIndex]
        holder.tahunAngkatan.text = currentItem.tahun.toString()

        val compendiumMajor = when (currentItem.jurusan) {
            "Rekayasa Perangkat Lunak" -> "RPL"
            "Desain Komunikasi Visual" -> "DKV"
            "Audio Video" -> "TAV"
            "Broadcasting" -> "BC"
            "Animasi" -> "ANI"
            "Teknik Komunikasi Jaringan" -> "TKJ"
            "Elektronika Industri" -> "EI"
            "Mekatronika" -> "MEK"
            else -> currentItem.jurusan
        }

        //ngide lagi
        val adjustedClassMajor = "$compendiumMajor $kelasIndex"
        holder.namaKelas.text = adjustedClassMajor

        when (compendiumMajor) {
            "RPL" -> holder.iconJurusan.setImageResource(R.mipmap.ic_rpl_foreground)
            "DKV" -> holder.iconJurusan.setImageResource(R.mipmap.ic_dkv_foreground)
            "TAV" -> holder.iconJurusan.setImageResource(R.mipmap.ic_tav_foreground)
            "BC" -> holder.iconJurusan.setImageResource(R.mipmap.ic_broadcast_foreground)
            "ANI" -> holder.iconJurusan.setImageResource(R.mipmap.ic_animasi_foreground)
            "TKJ" -> holder.iconJurusan.setImageResource(R.mipmap.ic_tkj_foreground)
            "EI" -> holder.iconJurusan.setImageResource(R.mipmap.ic_tei_foreground)
            "MEK" -> holder.iconJurusan.setImageResource(R.mipmap.ic_mekatro_foreground)
            else -> holder.iconJurusan.setImageResource(R.mipmap.ic_rpl_foreground)
        }
    }

    override fun getItemCount(): Int {
        return itemAngkatan.size * 2
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tahunAngkatan: TextView = itemView.findViewById(R.id.tvTahunAngkatan)
        val iconJurusan: ImageView = itemView.findViewById(R.id.tvLogoDetailJurusan)
        val namaKelas: TextView = itemView.findViewById(R.id.tvNamaDetailJurusan)
    }

}