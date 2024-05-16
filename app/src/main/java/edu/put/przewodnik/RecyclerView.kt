package edu.put.przewodnik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SzlakiAdapter(private val szlaki: Array<String>, private val szlakiObrazkiMap: Map<String, Int>, private val clickListener: (String) -> Unit) : RecyclerView.Adapter<SzlakiAdapter.SzlakViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SzlakViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return SzlakViewHolder(view)
    }

    override fun onBindViewHolder(holder: SzlakViewHolder, position: Int) {
        val szlak = szlaki[position]
        val obrazek = szlakiObrazkiMap[szlak] ?: R.drawable.camera_icon // Obrazek domyślny, jeśli nie znaleziono odpowiedniego
        holder.bind(szlak, obrazek, clickListener)
    }


    override fun getItemCount() = szlaki.size

    inner class SzlakViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(szlak: String, obrazek: Int, clickListener: (String) -> Unit) {
            itemView.findViewById<TextView>(R.id.szlakNameTextView).text = szlak
            itemView.findViewById<ImageView>(R.id.szlakImageView).setImageResource(obrazek)
            itemView.setOnClickListener { clickListener(szlak) }
        }
    }

}

