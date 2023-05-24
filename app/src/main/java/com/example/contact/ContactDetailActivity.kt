package com.example.contact

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.TextView
import com.example.contact.databinding.ActivityContactDetailBinding
import java.util.concurrent.Executors

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityContactDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        getData()
    }

    private fun getData(){
        var intent = intent.extras
        var name = intent!!.getString("name")
        var email = intent!!.getString("email")
        var phone = intent!!.getString("phone")
        var cell = intent!!.getString("cell")
        var picture = intent!!.getString("picture")
        with(binding){
            nameDetailTextView.text = name
            phoneDetailTextView.text = phone
            cellDetailTextView.text = cell
            emailDetailTextView.text = email
            picture?.let { urlToPicture(it) }
        }
    }

    private fun urlToPicture(pictureURL: String){
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null

        executor.execute{
            val imageURL = pictureURL
            try {

                val input = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(input)
                handler.post{
                    binding.pictureDetailImageView.setImageBitmap(image)
                }

            }catch (e: Exception){

                e.printStackTrace()

            }
        }
    }
}