package com.example.contact

import androidx.lifecycle.LiveData
import androidx.room.*

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