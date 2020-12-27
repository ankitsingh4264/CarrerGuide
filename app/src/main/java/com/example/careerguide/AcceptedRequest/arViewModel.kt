package com.example.careerguide.AcceptedRequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.careerguide.FirebaseDataRepository
import com.example.careerguide.beans.Users
import com.example.careerguide.pendingRequest.prAdapter

class arViewModel:ViewModel() {
    private  val FirebaseDatabase= FirebaseDataRepository()
    var marusers: MutableLiveData<ArrayList<Users>> = MutableLiveData()
    var mUserDetail: MutableLiveData<Users> = MutableLiveData()

    fun usersDetails(){
        mUserDetail=FirebaseDatabase.getUserDetails()
    }
    fun getusersar(){
        marusers=FirebaseDatabase.getuserpr()

    }
    fun updatepr(id:String,accepted:Int){
        FirebaseDatabase.updateuserpr(id,accepted)
    }
}