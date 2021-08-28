package com.kuria.myfirebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    var editTextName:EditText? = null
    var editTextEmail:EditText? = null
    var editTextIdNumber:EditText? = null
    var buttonSave:Button? = null
    var buttonView:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextName = findViewById(R.id.mEdtName)
        editTextEmail = findViewById(R.id.mEdtEmail)
        editTextIdNumber = findViewById(R.id.mEdtIdNumber)
        buttonSave = findViewById(R.id.mBtnSave)
        buttonView = findViewById(R.id.mBtnView)

        buttonSave!!.setOnClickListener{
            var name = editTextName!!.text.toString().trim()
            var email = editTextEmail!!.text.toString().trim()
            var id_number = editTextIdNumber!!.text.toString().trim()
            //Check if the user has filled all the inputs
            if (name.isEmpty() or email.isEmpty() or id_number.isEmpty()){
                Toast.makeText(this,"Please fill all the inputs",
                                        Toast.LENGTH_LONG).show()
            }else{
                //Continue to save data
                var time = System.currentTimeMillis().toString()
                //Prepare user data to be saved
                var user = User(name, email, id_number, time)
                //Create table/Chiled
                var ref:DatabaseReference = FirebaseDatabase.getInstance().
                                    getReference().child("Usets/$time")
                //Store data on the reference and check if the data was saved
                ref.setValue(user).addOnCompleteListener { task->
                if (task.isComplete){
                    Toast.makeText(this, "User saved successfully",
                        Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "Saving user failed",
                            Toast.LENGTH_LONG).show()
                }
                }
            }
        }

        buttonView!!.setOnClickListener {
            var tembea = Intent(this, UsersActivity::class.java)
            startActivity(tembea)
        }
    }
}