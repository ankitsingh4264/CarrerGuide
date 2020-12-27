package com.example.careerguide.Home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import com.example.careerguide.login.loginViewModel
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.fragment_find_mentor.*
import kotlinx.android.synthetic.main.fragment_mentor.view.*
import kotlinx.android.synthetic.main.mentor_item.*
import kotlinx.android.synthetic.main.mentor_item.view.*
import kotlinx.android.synthetic.main.mentor_item.view.edt_mentor_about
import kotlinx.android.synthetic.main.mentor_item.view.edt_mentor_headline
import kotlinx.android.synthetic.main.mentor_item.view.edt_mentor_name


class FindMentorFragment : Fragment() ,MentorsRV.onitemClick{


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_find_mentor, container, false)
    }
    private lateinit var findMentorVIewModel: FindMentorVIewModel
    lateinit var dapter:MentorsRV
    private var exmentlist:ArrayList<Users> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findMentorVIewModel= ViewModelProvider(requireActivity()).get(FindMentorVIewModel::class.java)
        pbfindmentor.visibility=View.VISIBLE

        findMentorVIewModel.getCategory()
        findMentorVIewModel.mCategorydata.observe(requireActivity(),
        Observer {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireActivity(),
                android.R.layout.simple_dropdown_item_1line, it
            )
            edtcategory.setAdapter(adapter)
            pbfindmentor.visibility=View.GONE
        })
        btnserch.setOnClickListener {
            if (edtcategory.text==null){
                Toast.makeText(requireContext(),"category field should not be empty",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            stagehandle()
            val currfield=edtcategory.text.toString()

            findMentorVIewModel.getMentor(currfield)
            findMentorVIewModel.mMentors.observe(requireActivity(),
            Observer {
                if (it!=null){
                    exmentlist=it
                    dapter=MentorsRV(it,
                          requireActivity(),
                            this
                           )
                    rvmentors.apply {
                        adapter=dapter
                        layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                    }

                }
            })
        }
    }
    fun stagehandle(){
    btnserch.visibility=View.GONE
    edtcategory.visibility=View.GONE
        rvmentors.visibility=View.VISIBLE
   }

    override fun onItemClicked(position: Int) {

        showdialog(position)
    }

    private fun showdialog(position: Int) {
        val curr=exmentlist.get(position)
        val mDialogView = LayoutInflater.from(requireContext()).inflate(
            R.layout.fragment_mentor,
            null
        )
        if (curr.imagepath!=null){
            Glide.with(requireActivity()).load(curr.imagepath).into(img_profile_mentor)
        }
        mDialogView.edt_mentor_field.setText(curr.fieldsMentoring)
        mDialogView.edt_mentor_name.setText(curr.name)
        mDialogView.edt_mentor_headline.setText(curr.headline)
        mDialogView.edt_mentor_about.text = curr.mentorAbout
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
        val mAlertDialog =  mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)
        mDialogView.btncancelapt.setOnClickListener {
            mAlertDialog.dismiss()
        }
        mDialogView.btnconfirmapt.setOnClickListener {
            findMentorVIewModel.createpr(curr.id!!)
            Toast.makeText(requireActivity(),"request sent", Toast.LENGTH_SHORT).show()
        }


    }


}