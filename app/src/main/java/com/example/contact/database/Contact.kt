package com.example.contact.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact (

    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val location: String,
    val email: String,
    val phone: String,
    val cell: String,
    val picture: String

)