package com.example.contact.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.database.Contact
import com.example.contact.databinding.CustomRowBinding
import java.util.concurrent.Executors

class ListAdapter(clikListener: ClickListener): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var contactList = emptyList<Contact>()
    private var clikListener: ClickListener = clikListener

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

                val executor = Executors.newSingleThreadExecutor()
                val handler = Handler(Looper.getMainLooper())
                var image: Bitmap? = null

                executor.execute{

                    val imageURL = picture
                    try {

                        val input = java.net.URL(imageURL).openStream()
                        image = BitmapFactory.decodeStream(input)
                        handler.post{
                            binding.imageView.setImageBitmap(image)
                        }

                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }

            itemView.setOnClickListener{
                clikListener.clickedItem(contactList[position])
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

    interface ClickListener{
        fun clickedItem(contact: Contact)
    }
}