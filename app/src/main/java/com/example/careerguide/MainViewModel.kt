package com.example.careerguide

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.careerguide.beans.Users
import com.google.firebase.firestore.auth.User

class MainViewModel: ViewModel()  {
    private  val FirebaseDatabase= FirebaseDataRepository()
    var musers :MutableLiveData<Users> = MutableLiveData()
    fun getcurruser(){
        musers=FirebaseDatabase.getcurruser()

    }
}