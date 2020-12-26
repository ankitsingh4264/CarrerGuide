package com.example.careerguide.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import kotlinx.android.synthetic.main.fragment_find_mentor.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import kotlinx.android.synthetic.main.mentor_item.view.*
import java.text.FieldPosition

class MentorsRV(val list:ArrayList<Users>,val context:Context,val clickListener: onitemClick) : RecyclerView.Adapter<MentorsRV.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.mentor_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(clickListener,list.get(position),position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(clickListener: onitemClick,curruser:Users,position: Int){
           itemView.edit_name.setText(curruser.name)
            itemView.edt_mentor_headline.setText(curruser.fieldsMentoring)
            itemView.btnserch.setOnClickListener {
                clickListener.onItemClicked(position)
            }

        }




    }
    interface onitemClick{
        fun onItemClicked(position: Int)
    }

}