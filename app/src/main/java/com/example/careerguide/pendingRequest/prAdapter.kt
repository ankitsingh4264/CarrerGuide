package com.example.careerguide.pendingRequest

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.mentor_item.view.*
import kotlinx.android.synthetic.main.pr_item.view.*

class prAdapter (val list:ArrayList<Users>, val context: Context, val clickListener: onitemClick) : RecyclerView.Adapter<prAdapter.ViewHolder>() {
   val contex=context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.pr_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener  ,list.get(position),position,context)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(clickListener:onitemClick, curruser: Users, position: Int,context: Context){
            itemView.pr_edt_name.setText(curruser.name)
            itemView.pr_edt_headline.setText(curruser.fieldsMentoring)
            if (curruser.imagepath!=null){
                Glide.with(context).load(curruser.imagepath)
                        .into(itemView.pr_profile_img)
            }
            itemView.pr_accept.setOnClickListener {
                clickListener.onItemClicked(position,true)
            }
            itemView.pr_reject.setOnClickListener {
                clickListener.onItemClicked(position,false)
            }

        }




    }
    interface onitemClick{
        fun onItemClicked(position: Int,accepted:Boolean)
    }




}