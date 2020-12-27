package com.example.careerguide.Home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import kotlinx.android.synthetic.main.mentor_item.view.*

class MentorsRV(val list:ArrayList<Users>,val context:Context,val clickListener: onitemClick) : RecyclerView.Adapter<MentorsRV.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.mentor_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(clickListener  ,list.get(position),position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(clickListener: onitemClick, curruser:Users, position: Int){
            Log.i("ankit",curruser.toString())
           itemView.edt_mentor_name.setText(curruser.name)
            itemView.edt_mentor_headline.setText(curruser.fieldsMentoring)
            itemView.btnbookappt.setOnClickListener {
                clickListener.onItemClicked(position)
            }

        }




    }
    interface onitemClick{
        fun onItemClicked(position: Int)
    }

}