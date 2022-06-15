package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {

    lateinit var emailsiAuth : EditText
    lateinit var passwordsiAuth : EditText
    lateinit var btn_signin : Button
    lateinit var TV_signup : TextView
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        supportActionBar!!.hide()
        emailsiAuth = findViewById(R.id.ET_emailSI)
        passwordsiAuth = findViewById(R.id.ET_passwordSI)
        btn_signin = findViewById(R.id.Btn_signin)
        TV_signup = findViewById(R.id.TV_signup)
        mAuth = FirebaseAuth.getInstance()

        TV_signup.setOnClickListener {
            var intent = Intent(this@SignIn, AuthenticationActivity::class.java)
            startActivity(intent)
        }

        btn_signin.setOnClickListener {
            val email = emailsiAuth.text.toString()
            val password = passwordsiAuth.text.toString()


            if (email != "" && password != ""){
                signIn(email, password)
            } else {
                Toast.makeText(this, "Masih ada field yang kosong", Toast.LENGTH_LONG).show()
            }
        }



    }

    private fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,  "Login berhasil",Toast.LENGTH_SHORT).show()
                    emailsiAuth.text.clear()
                    passwordsiAuth.text.clear()
                    startActivity(Intent(this, RvUserActivity::class.java))
                // Sign in success, update UI with the signed-in user's information
                } else {
                    Toast.makeText(this, "Login gagal",Toast.LENGTH_SHORT).show()

                }
            }
    }
}