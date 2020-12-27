package com.example.careerguide.Home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.careerguide.Profile.profileViewModel
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_be_mentor.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.edit_profile_headline


lateinit var auth : FirebaseAuth

class beMentor : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_be_mentor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var use: Users = Users()

        var n:String?=null
        var head:String?=null
        var pho:String?=null
        var uriimg:String?=null
        var field:String?=null
        var about:String?=null
        var i:String?=null


        com.example.careerguide.Profile.auth = FirebaseAuth.getInstance()

        com.example.careerguide.Profile.profilemvvm = ViewModelProvider(requireActivity()).get(profileViewModel::class.java)

        com.example.careerguide.Profile.profilemvvm.usersDetails()
        com.example.careerguide.Profile.profilemvvm.mUserDetail.observe(
            requireActivity()
        ) {
            field=it.fieldsMentoring
            if(it.fieldsMentoring!=null)
            {

                var s1:String?=""
                var array:ArrayList<String> =   ArrayList()
                for (i in it.fieldsMentoring)
                {
                    if(i==',' )
                    {
                        if (s1 != null) {
                            array.add(s1!!)
                            s1=""
                        }
                    }
                    else
                    {
                        s1 += i

                    }
                }
                if (s1 != null) {
                    array?.add(s1!!)
                }

                edit_field1.setText(array?.get(0))
                edit_field2.setText(array?.get(1))
                edit_field3.setText(array?.get(2))
            }
            if(it.mentorAbout!=null)
            {
                edit_about.setText(it.mentorAbout)
            }

            text_name.setText(it.name)
            n=it.name
            text_headline.setText(it.headline)
            head=it.headline
            pho=it.phone
            uriimg=it.imagepath
            i=it.id
        }


        button_save_field.setOnClickListener {
            if (edit_field1==null && edit_field1.text.length==0)
            {
                edit_field1.error="This should not be empty"
            }
            else {
                if(edit_field1!=null)
                {
                    field=edit_field1.text.toString()
                }
                if(edit_field2!=null)
                {
                    field+=","+edit_field2.text.toString()
                }
                if(edit_field3!=null)
                {
                    field+=","+edit_field3.text.toString()
                }
                use = Users(
                        name = n,
                        phone = pho,
                        headline = head,
                        imagepath = uriimg,
                        fieldsMentoring = field,
                        mentorAbout = edit_about.text.toString(),
                        id = i
                )

                com.example.careerguide.Profile.profilemvvm.insertUser(use)
                Toast.makeText(
                        requireActivity(),
                        "Upload Successful",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
