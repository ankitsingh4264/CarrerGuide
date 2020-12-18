package com.example.careerguide.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.careerguide.FirebaseDataRepository

class signUpViewModel: ViewModel() {
    private  val FirebaseDatabase= FirebaseDataRepository()
    //var mSignin: MutableLiveData<Boolean> = MutableLiveData()
    var mUserExist : MutableLiveData<Boolean> = MutableLiveData()

//    fun signInWithPhone(credential: PhoneAuthCredential, users: Users){
//        mSignin =  FirebaseDatabase.signInPhone(credential,users)
//    }



    fun userExist(phone:String){
        mUserExist=  FirebaseDatabase.checkUserExist(phone)
    }
}