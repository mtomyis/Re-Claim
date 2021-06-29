package com.conceptdesign.re_claim.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.conceptdesign.re_claim.Model.DetailReimbursment
import com.conceptdesign.re_claim.R
import com.conceptdesign.re_claim.UpdateActivity
import kotlinx.android.synthetic.main.item_detail.view.*

class ListMyClaimAdapterDetail(val lstReimburs:List<DetailReimbursment>, val itemClickListener: UpdateActivity):
        RecyclerView.Adapter<ListMyClaimAdapterDetail.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMyClaimAdapterDetail.MyHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return MyHolder(v)
    }

    override fun getItemCount(): Int = lstReimburs?.size?:0

    override fun onBindViewHolder(holder: ListMyClaimAdapterDetail.MyHolder, position: Int) {
        holder.bind(lstReimburs?.get(position),itemClickListener)
    }

    inner class MyHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(get:DetailReimbursment?, clickListener: UpdateActivity){
            itemView.id_txt_detail_claim.text = get?.keperluan
            val total ="Rp. ${get?.nominal}"
            itemView.id_txt_total_detail.text = total

            itemView.setOnClickListener { clickListener.onItemClicked(get) }
        }
    }
}