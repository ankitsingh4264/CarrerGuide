package com.example.careerguide.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.careerguide.R
import com.example.careerguide.login.loginViewModel
import kotlinx.android.synthetic.main.fragment_find_mentor.*


class FindMentorFragment : Fragment() ,MentorsRV.onitemClick{


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_find_mentor, container, false)
    }
    private lateinit var findMentorVIewModel: FindMentorVIewModel
    lateinit var adapter:MentorsRV

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
                       adapter=MentorsRV(it,
                          requireActivity(),
                          this )
                    rvmentors.apply {
                        adapter=adapter
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

    }


}