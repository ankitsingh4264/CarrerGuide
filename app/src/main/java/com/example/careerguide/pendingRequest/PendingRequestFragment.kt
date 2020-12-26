package com.example.careerguide.pendingRequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.careerguide.Home.FindMentorVIewModel
import com.example.careerguide.Home.MentorsRV
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import kotlinx.android.synthetic.main.fragment_pending_request.*

class PendingRequestFragment : Fragment() ,prAdapter.onitemClick{

    private lateinit var prViewModel: prViewModel
    lateinit var dapter: prAdapter
    private var exmentlist:ArrayList<Users> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_pending_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prViewModel= ViewModelProvider(requireActivity()).get(prViewModel::class.java)
        prViewModel.getuserspr()
        prViewModel.mprusers.observe(requireActivity(),
        Observer {
            exmentlist=it
            if (it!=null){
                dapter= prAdapter(it,
                requireActivity(),
                this)
                pr_rv.apply {
                    adapter=dapter
                    layoutManager=
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                }
            }
        })


    }

    override fun onItemClicked(position: Int, accepted: Boolean) {
       var acceptance=0;
        if (accepted) acceptance=1;
        prViewModel.updatepr(exmentlist.get(position).id!!,acceptance)

    }


}