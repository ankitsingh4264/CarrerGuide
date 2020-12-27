package com.example.careerguide.AcceptedRequest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.careerguide.Profile.profilemvvm
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import kotlinx.android.synthetic.main.fragment_accepted_request.*
import kotlinx.android.synthetic.main.fragment_profile.*


class acceptedRequest : Fragment(),arAdapter.onitemClick {

    private lateinit var armvvm: arViewModel
    lateinit var dapter: arAdapter
    private var list:ArrayList<Users> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accepted_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        armvvm= ViewModelProvider(requireActivity()).get(arViewModel::class.java)
        armvvm.getusersar()
        armvvm.marusers.observe(requireActivity(),
                Observer {
                    list=it
                    if (it!=null){
                        dapter= arAdapter(it,
                                requireActivity(),
                                this)
                        ar_rv.apply {
                            adapter=dapter
                            layoutManager=
                                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                        }
                    }
                })

    }



    override fun onItemClicked(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        startActivity(intent)
    }

}