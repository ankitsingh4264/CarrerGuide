package com.example.careerguide

import androidx.lifecycle.MutableLiveData
import com.example.careerguide.beans.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDataRepository {
    private val firestoreDB =FirebaseFirestore.getInstance()
    private val auth= FirebaseAuth.getInstance()

    fun signInPhone(credential: PhoneAuthCredential, users: Users) : MutableLiveData<Boolean> {
        val result: MutableLiveData<Boolean> = MutableLiveData()
        var userid:String?
        auth.signInWithCredential(credential)
            .addOnCompleteListener { it->
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    userid=it.result!!.user!!.uid
                    firestoreDB.collection("users").document(userid!!).set(users).addOnSuccessListener {
                        result.value=true;
                    }.addOnFailureListener{
                        result.value=false
                    }
                } else {
                    result.value=false
                }
            }
        return  result
    }

    fun  checkUserExist(phone:String):MutableLiveData<Boolean>{
        val exist:MutableLiveData<Boolean> = MutableLiveData()
        firestoreDB.collection("users").whereEqualTo("phone",phone).get()
                .addOnSuccessListener {
                    exist.value = it.size()>0
                }
        return exist
    }

}
