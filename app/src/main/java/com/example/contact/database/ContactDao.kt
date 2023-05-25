package com.example.contact.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contact.database.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addContact(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<Contact>>

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAllContact()

}