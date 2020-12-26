package com.example.careerguide.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.careerguide.FirebaseDataRepository
import com.example.careerguide.beans.Users

class FindMentorVIewModel: ViewModel() {
    private  val FirebaseDatabase= FirebaseDataRepository()


    var mCategorydata:MutableLiveData<ArrayList<String>> = MutableLiveData()
    var mMentors:MutableLiveData<ArrayList<Users>> = MutableLiveData()

    fun getCategory(){
        mCategorydata=FirebaseDatabase.getCategory()
    }
    fun getMentor(field: String){
        mMentors=FirebaseDatabase.getMentor(field)
    }
}