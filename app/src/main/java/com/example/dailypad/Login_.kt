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

class Login_ : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth=FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser!=null){
            navigatetoHome()
        }
        val email=findViewById<EditText>(R.id.email)
        val pass=findViewById<EditText>(R.id.pass)
        val login=findViewById<Button>(R.id.login)
        val createnew=findViewById<TextView>(R.id.createnew)
        firebaseAuth=FirebaseAuth.getInstance()
        login.setOnClickListener(View.OnClickListener {
            val emaill=email.text.toString()
            val passward=pass.text.toString()
            if(emaill.isNotEmpty()&&passward.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(emaill,passward).addOnCompleteListener{
                    if(it.isSuccessful){
                        val userEmail=emaill
                        startActivity(Intent(this,MainActivity::class.java))
                    }else{
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(this,"Enter Correct values", Toast.LENGTH_LONG).show()
            }

        })
        createnew.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,createAcc::class.java))
        })
        authStateListener=FirebaseAuth.AuthStateListener { auth->
            val user=auth.currentUser
            if(user!=null){
                navigatetoHome()
            }
        }
    }

    fun navigatetoHome(){

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth=FirebaseAuth.getInstance()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth=FirebaseAuth.getInstance()
        firebaseAuth.removeAuthStateListener(authStateListener)

    }
}
