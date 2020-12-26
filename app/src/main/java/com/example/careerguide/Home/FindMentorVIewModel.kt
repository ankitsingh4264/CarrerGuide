package com.example.careerguide.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.careerguide.FirebaseDataRepository

class FindMentorVIewModel: ViewModel() {
    private  val FirebaseDatabase= FirebaseDataRepository()


    var mCategorydata:MutableLiveData<ArrayList<String>> = MutableLiveData()
    fun getCategory(){
        mCategorydata=FirebaseDatabase.getCategory()
    }
}