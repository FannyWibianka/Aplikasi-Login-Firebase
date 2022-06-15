package com.example.firebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

class UserAdapter (val context: Context,
      val userList: ArrayList<User>): RecyclerView
      .Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvName = itemView
            .findViewById<TextView>(R.id.TV_listusername)
        val tvEmail = itemView
            .findViewById<TextView>(R.id.TV_listuseremail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(
                R.layout.activity_list_user,
                parent, false
            )
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.tvName.text = userList[position].name
        holder.tvEmail.text = userList[position].email
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}



