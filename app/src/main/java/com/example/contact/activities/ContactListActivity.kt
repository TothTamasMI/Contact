package com.example.contact.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.database.Contact
import com.example.contact.database.ContactDatabase
import com.example.contact.adapters.ListAdapter
import com.example.contact.databinding.ActivityContactListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

class ContactListActivity : AppCompatActivity(), ListAdapter.ClickListener {

    private lateinit var binding: ActivityContactListBinding
    private lateinit var contactDb : ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ListAdapter(this)
        val recyclerView = binding.recyclerview
        val layoutManager = LinearLayoutManager(this)

        contactDb = ContactDatabase.getDatabase(this)
        recyclerView.adapter = adapter
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        //DELETE ALL COTACTS
        //GlobalScope.launch(Dispatchers.IO) { contactDb.contactDao().deleteAllContact()}

        contactDb.contactDao().readAllData().observe(this, Observer { contact ->
            adapter.setData(contact)
        })

        binding.floatingActionButton.setOnClickListener{
            addContact()
        }
    }

    private fun addContact(){

        GlobalScope.launch(Dispatchers.IO) {

            val apiResponse = URL("https://randomuser.me/api").readText()
            println(apiResponse)
            val contact = getDataFromResponse(apiResponse)
            contactDb.contactDao().addContact(contact)

        }
    }

    private fun getDataFromResponse(response: String): Contact {

        val id = 0
        val name = findWordInResponse(response,"title") + " " + findWordInResponse(response,"first") + " " + findWordInResponse(response,"last")
        val location = findWordInResponse(response, "city") + ", " + findWordInResponse(response.substring(response.indexOf("name") + 4), "name") + " " + findWordInResponse(response, "numbe").substring(0,findWordInResponse(response, "numbe").length-1)
        val email = findWordInResponse(response, "email")
        val phone = findWordInResponse(response, "phone")
        val cell = findWordInResponse(response, "cell")
        val picture = findWordInResponse(response, "large")

        return Contact(id,name, location, email,phone,cell,picture)
    }

    private fun findWordInResponse(input: String, word: String):String{

        val index = input.indexOf(word)
        val firstHalf = input.substring(index + word.length + 3)
        val secondHalf = firstHalf.substring(0, firstHalf.indexOf("\""))

        return secondHalf
    }

    override fun clickedItem(contact: Contact) {

        startActivity(Intent(this, ContactDetailActivity::class.java)
            .putExtra("name", contact.name)
            .putExtra("email", contact.email)
            .putExtra("phone", contact.phone)
            .putExtra("cell", contact.cell)
            .putExtra("picture", contact.picture))

    }
}