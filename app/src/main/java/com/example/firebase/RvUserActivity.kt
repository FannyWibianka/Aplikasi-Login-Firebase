package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class RvUserActivity : AppCompatActivity() {


        lateinit var userRecyclerView: RecyclerView
        lateinit var userList : ArrayList<User>
        lateinit var adapter : UserAdapter
        lateinit var mDbRef : DatabaseReference

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_recyclerview_user)

            mDbRef = FirebaseDatabase.getInstance().getReference()
            userList = ArrayList()
            userRecyclerView = findViewById(R.id.RV_list)
            adapter = UserAdapter(this, userList)
            userRecyclerView.layoutManager = LinearLayoutManager(this)
            userRecyclerView.adapter = adapter


            // Read data from realtime database
            mDbRef.child("User").addValueEventListener(
                object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        userList.clear()
                        for (postSnapshot in snapshot.children) {
                            val user = postSnapshot.getValue(User::class.java)
                            userList.add(user!!)
                        }
                        adapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })


        }
    }

