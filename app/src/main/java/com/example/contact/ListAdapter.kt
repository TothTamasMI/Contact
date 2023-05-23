package com.example.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.databinding.CustomRowBinding

class ListAdapter(): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var contactList = emptyList<Contact>()

    inner class MyViewHolder (val binding: CustomRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(contactList[position]){
                binding.nameTextView.text = name
                binding.emailTextView.text = email
                binding.locationTextView.text = location
            }
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    fun setData(contact: List<Contact>){
        this.contactList = contact
        notifyDataSetChanged()
    }
}