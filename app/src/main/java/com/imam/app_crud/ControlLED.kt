package com.imam.app_crud

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.imam.app_crud.R.*

class ControlLED :  AppCompatActivity() {

    private lateinit var onButton: Button
    private lateinit var offButton: Button
    private lateinit var imageView: ImageView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_control_led)

        onButton = findViewById(id.buttonOn)
        offButton = findViewById(id.buttonOff)
        imageView = findViewById(id.imageView)

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Relay_STATUS")

        // Set button click listeners
        onButton.setOnClickListener {
            databaseReference.setValue(1)
        }

        offButton.setOnClickListener {
            databaseReference.setValue(0)
        }

        // Listen for changes in the relay status
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val relayStatus = dataSnapshot.getValue(Int::class.java)
                if (relayStatus != null) {
                    if (relayStatus == 1) {
                        imageView.setImageResource(drawable.lamp_on)
                    } else {
                        imageView.setImageResource(drawable.lamp_off)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ControlLED, "Failed to read relay status", Toast.LENGTH_SHORT).show()
            }
        })
    }
}