package com.imam.app_crud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var ref : DatabaseReference
    private lateinit var inputNama: EditText
    private lateinit var inputStatus: EditText
    private lateinit var controlLEDButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inputNama = findViewById(R.id.inputNama)
        inputStatus = findViewById(R.id.inputStatus)
        ref = FirebaseDatabase.getInstance().getReference("users")
        controlLEDButton = findViewById(R.id.btnControlLED)
        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
          savedata()
        }
        controlLEDButton.setOnClickListener {
            val intent = Intent(this, ControlLED::class.java)
            startActivity(intent)
        }
    }


   private fun savedata() {

       val nama = inputNama.text.toString()
       val status = inputStatus.text.toString()

       if (nama.isNotEmpty() && status.isNotEmpty()) {
           val user = Users(nama, status)
           val userid = ref.push().key ?: ""

           ref.child(userid).setValue(user).addOnCompleteListener { task ->
               if (task.isSuccessful) {
                   inputNama.setText("")
                   inputStatus.setText("")
                   Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show()
               } else {
                   Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
               }
           }
       } else {
           Toast.makeText(this, "Nama dan Status tidak boleh kosong", Toast.LENGTH_SHORT).show()
       }
   }
}




