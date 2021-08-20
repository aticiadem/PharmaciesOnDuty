package com.adematici.pharmaciesonduty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adematici.pharmaciesonduty.databinding.PharmacyRecyclerRowBinding
import com.adematici.pharmaciesonduty.model.Result

class PharmacyAdapter(private val pharmacyList: ArrayList<Result>): RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder>() {

    inner class PharmacyViewHolder(val itemBinding: PharmacyRecyclerRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {
        val binding = PharmacyRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PharmacyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        holder.itemBinding.cardPharmacyName.text = pharmacyList[position].name
        holder.itemBinding.cardTextAddress.text = pharmacyList[position].address
        holder.itemBinding.cardTextPhone.text = pharmacyList[position].phone
    }

    override fun getItemCount(): Int {
        return pharmacyList.size
    }

}