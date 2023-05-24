package com.example.contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ContactDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        getData()
    }

    private fun getData(){
        var intent = intent.extras
        var name = intent!!.getString("name")
        val tv = findViewById<TextView>(R.id.tv)
        tv.text = name
    }
}