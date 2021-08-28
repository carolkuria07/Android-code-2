package com.kuria.myfirebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*

class UsersActivity : AppCompatActivity() {
    var usersListView:ListView? = null
    var users:ArrayList<User>? = null
    var adapter:CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        usersListView = findViewById(R.id.mListUsers)
        users = ArrayList()
        adapter = CustomAdapter(this, users!!)
        //Make the reference to the database
        var reference:DatabaseReference = FirebaseDatabase.getInstance().
                            getReference().child("Users")
        //Start fetching the data
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                users!!.clear()
                //Loop through fetched data as you display it
                for (snap in snapshot.children){
                    var user = snap.getValue(User::class.java)
                    users!!.add(user!!)
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UsersActivity,
                    "Please contact ADMIN FOR HELP", Toast.LENGTH_LONG).show()
            }
        })
        usersListView!!.adapter = adapter
    }
}