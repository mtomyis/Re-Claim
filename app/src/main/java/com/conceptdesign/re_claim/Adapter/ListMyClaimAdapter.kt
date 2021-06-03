package com.conceptdesign.re_claim.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.conceptdesign.re_claim.MainActivity
import com.conceptdesign.re_claim.Model.Reimbursement
import com.conceptdesign.re_claim.R
import kotlinx.android.synthetic.main.item_claim.view.*

class ListMyClaimAdapter(val lstReimburs:List<Reimbursement>, val itemClickListener: MainActivity):
    RecyclerView.Adapter<ListMyClaimAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMyClaimAdapter.MyHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_claim, parent, false)
        return MyHolder(v)
    }

    override fun getItemCount(): Int = lstReimburs?.size?:0

    override fun onBindViewHolder(holder: ListMyClaimAdapter.MyHolder, position: Int) {
        holder.bind(lstReimburs?.get(position),itemClickListener)
    }

    inner class MyHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        fun bind(get:Reimbursement?,clickListener:MainActivity){
            itemView.id_txt_judul_claim.text = get?.reimburs
            itemView.id_txt_tanggal.text = get?.tgl
            val total ="Rp. ${get?.total}"
            itemView.id_txt_total.text = total
        }
    }
}