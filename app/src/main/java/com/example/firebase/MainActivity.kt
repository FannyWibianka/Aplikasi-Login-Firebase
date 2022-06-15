package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var ET_email : EditText
    private lateinit var ET_nama : EditText
    private lateinit var Btn_save : Button
    private lateinit var mDbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()
        ET_email = findViewById(R.id.ET_email)
        ET_nama = findViewById(R.id.ET_name)
        Btn_save = findViewById(R.id.Btn_save)
        mDbRef = FirebaseDatabase.getInstance().reference

        Btn_save.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        var email = ET_email.text.toString()
        var name = ET_nama.text.toString()
        var uuid = UUID.randomUUID().toString()


        if (email.isNullOrBlank()){
            ET_email.error = "email harus diisi"
            ET_email.requestFocus()
        }else{
            name.isNullOrBlank()
            ET_nama.error = "nama harus diisi"
            ET_nama.requestFocus()
        }
        mDbRef.child("user").child(uuid)
            .setValue(User(name, email))
        Toast.makeText(this, "Data berhasil tersimpan", Toast.LENGTH_LONG).show()
        ET_email.text.clear()
        ET_nama.text.clear()
    }


}