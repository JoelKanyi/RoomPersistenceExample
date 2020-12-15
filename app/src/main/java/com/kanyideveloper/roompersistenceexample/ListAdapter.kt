package com.kanyideveloper.roompersistenceexample

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kanyideveloper.roompersistenceexample.data.User
import kotlinx.android.synthetic.main.recycler_row.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

     private var list = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]

        holder.itemView.userID.text = "${currentItem.id}."
        holder.itemView.firstN.text = currentItem.firstName
        holder.itemView.lastN.text = currentItem.lastName
        holder.itemView.ageN.text = "(${currentItem.age} years)"
    }

    override fun getItemCount(): Int {
       return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}

    fun setData(userList: List<User>){
        list = userList
        notifyDataSetChanged()
    }
}