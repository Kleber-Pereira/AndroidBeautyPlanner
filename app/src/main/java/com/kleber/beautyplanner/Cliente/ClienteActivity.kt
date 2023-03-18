package com.kleber.beautyplanner.Cliente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.kleber.beautyplanner.R

class ClienteActivity : AppCompatActivity() {
    //private lateinit var btnInsertData: Button
   // private lateinit var btnFetchData: Button



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente)


       // btnInsertData = findViewById(R.id.btnInsertData)
       // btnFetchData = findViewById(R.id.btnFetchData)

       /* btnInsertData.setOnClickListener{
            val intent = Intent(this, InserirActivity::class.java)
            startActivity(intent)
        }*/

        /*btnFetchData.setOnClickListener {
            val intent = Intent(this, BuscarActivity::class.java)
            startActivity(intent)
        }*/

    }
}