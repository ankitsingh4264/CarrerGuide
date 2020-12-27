package com.example.careerguide.AcceptedRequest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import kotlinx.android.synthetic.main.ar_item.view.*
import kotlinx.android.synthetic.main.pr_item.view.*

class arAdapter(val list:ArrayList<Users>, val context: Context, val clickListener: acceptedRequest) : RecyclerView.Adapter<arAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ar_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, list.get(position), position,context)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(clickListener: acceptedRequest, curruser: Users, position: Int,context: Context) {
            itemView.ar_edt_name.setText(curruser.name)
            itemView.ar_edt_headline.setText(curruser.fieldsMentoring)
            if(curruser.imagepath!=null)
            {
                Glide.with(context).load(curruser.imagepath)
                        .into(itemView.ar_profile_img)
            }
            itemView.button_call.setOnClickListener {
                clickListener.onItemClicked(true)
            }

        }


    }

    interface onitemClick {
        fun onItemClicked(position: Int, accepted: Boolean)
    }

}




