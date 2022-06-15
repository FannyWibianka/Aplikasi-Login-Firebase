package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AuthenticationActivity : AppCompatActivity() {

    lateinit var emailAuth: EditText
    lateinit var nameAuth: EditText
    lateinit var etPassword: EditText
    lateinit var btn_signup: Button
    lateinit var Tv_signin : TextView
    lateinit var mAuth: FirebaseAuth
    lateinit var mDebRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)


        supportActionBar!!.hide()
        emailAuth = findViewById(R.id.ET_email)
        nameAuth = findViewById(R.id.ET_name)
        etPassword = findViewById(R.id.ET_password)
        btn_signup = findViewById(R.id.Btn_signup)
        Tv_signin = findViewById(R.id.TV_signin)
        mAuth = FirebaseAuth.getInstance()
        mDebRef = FirebaseDatabase.getInstance().reference


        Tv_signin.setOnClickListener {
            var intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        btn_signup.setOnClickListener {
            auth()

            val name = nameAuth.text.toString()
            val email = emailAuth.text.toString()
            val password = etPassword.text.toString()

            if (name != "" && email != "" && password != "") {
                signUp(email, password)
            } else {
                Toast.makeText(this, "Masih ada field yang kosong", Toast.LENGTH_LONG).show()
            }
        }

    }


    private fun auth(){
         val name = nameAuth.text.toString()
         val email = emailAuth.text.toString()
         val uid = mAuth.uid.toString()

        mDebRef.child("User").child(uid).setValue(Auth(name, email, uid))
        Toast.makeText(this, "Data berhasil tersimpan", Toast.LENGTH_LONG).show()
    }

    private fun signUp(email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "SignUp berhasil", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@AuthenticationActivity, SignIn::class.java)
                    startActivity(intent)
//                    auth()
                    nameAuth.text.clear()
                    emailAuth.text.clear()
                    etPassword.text.clear()


                } else {
                    Toast.makeText(this, "SignUp gagal", Toast.LENGTH_LONG).show()
                }
            }
    }
}