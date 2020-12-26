package com.example.careerguide.Profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.careerguide.FirebaseDataRepository
import com.example.careerguide.beans.Users
import com.google.firebase.firestore.auth.User

class profileViewModel: ViewModel() {

    private  val FirebaseDatabase= FirebaseDataRepository()
    var mUserDetail: MutableLiveData<Users> = MutableLiveData()
    var mProfileAdded : MutableLiveData<Boolean> = MutableLiveData()

    fun usersDetails(){
        mUserDetail=FirebaseDatabase.getUserDetails()
    }

    fun insertUser(user: Users){
        mProfileAdded=FirebaseDatabase.userUpload(user)

    }

    fun uploadprofileToFirebase(imageUri: Uri?, path: String) {
        FirebaseDatabase.uploadProfileToFirebase(imageUri, path)
    }
}