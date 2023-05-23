package com.example.contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.databinding.ActivityContactListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var contactDb : ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactDb = ContactDatabase.getDatabase(this)

        val adapter = ListAdapter()
        val recyclerView = binding.recyclerview
        val layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        contactDb.contactDao().readAllData().observe(this, Observer { contact ->
            adapter.setData(contact)
        })

        binding.floatingActionButton.setOnClickListener{
            addContact()
        }
    }

    private fun addContact(){
        val id = 0
        val name = "Jimmy"
        val location = "Debrecen"
        val email = "jimmy@gmail.com"
        val phone = "0630-1234567"
        val cell = "0620-1234567"
        val picture = "https://img.freepik.com/free-vector/businessman-get-idea_1133-350.jpg?"

        val contact = Contact(id, name, location, email, phone, cell, picture)

        GlobalScope.launch(Dispatchers.IO) {
            contactDb.contactDao().addContact(contact)

        }
    }

    private fun readAllContact(){

    }
}