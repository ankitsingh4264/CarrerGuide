package com.example.careerguide.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.careerguide.FirebaseDataRepository
import com.example.careerguide.beans.Users
import com.google.firebase.auth.PhoneAuthCredential

class loginViewModel : ViewModel() {
    private  val FirebaseDatabase= FirebaseDataRepository()
    //var mSignin: MutableLiveData<Boolean> = MutableLiveData()
    var mUserExist : MutableLiveData<Boolean> = MutableLiveData()

//    fun signInWithPhone(credential: PhoneAuthCredential, users: Users){
//        mSignin =  FirebaseDatabase.signInPhone(credential,users)
//    }
//
//

    fun UserExist(phone:String){
        mUserExist=  FirebaseDatabase.checkUserExist(phone)
    }

}