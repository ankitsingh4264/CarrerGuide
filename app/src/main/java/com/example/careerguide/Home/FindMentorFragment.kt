package com.example.careerguide.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.careerguide.R
import com.example.careerguide.login.loginViewModel
import kotlinx.android.synthetic.main.fragment_find_mentor.*


class FindMentorFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_find_mentor, container, false)
    }
    private lateinit var findMentorVIewModel: FindMentorVIewModel

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



    }



}