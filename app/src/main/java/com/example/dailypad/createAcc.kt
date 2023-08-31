package com.example.dailypad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class createAcc : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acc)
        val email=findViewById<EditText>(R.id.createemail)
        val pass=findViewById<EditText>(R.id.createpass)
        val createbtn=findViewById<Button>(R.id.createacc)
        val alreadyhaveacc=findViewById<TextView>(R.id.alreadyhaveaccount)
        firebaseAuth= FirebaseAuth.getInstance()
        createbtn.setOnClickListener(View.OnClickListener {
            val email=email.text.toString()
            val pass=pass.text.toString()
            if(email.isNotEmpty()&&pass.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this,Login_::class.java))
                    }else{
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(this,"Kindly enter your email and pass", Toast.LENGTH_LONG).show()
            }
        })
        alreadyhaveacc.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,Login_::class.java))
        })
    }
}