package com.example.careerguide.pendingRequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.careerguide.FirebaseDataRepository
import com.example.careerguide.beans.Users

class prViewModel: ViewModel() {
    private  val FirebaseDatabase= FirebaseDataRepository()
    var mprusers:MutableLiveData<ArrayList<Users>> = MutableLiveData()
      fun getuserspr(){
          mprusers=FirebaseDatabase.getuserpr()

    }
    fun updatepr(id:String,accepted:Int){
        FirebaseDatabase.updateuserpr(id,accepted)
    }
}