package com.example.careerguide

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.careerguide.beans.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import java.util.ArrayList

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

    fun getCategory():MutableLiveData<ArrayList<String>>{
        val data:MutableLiveData<ArrayList<String>> = MutableLiveData()
        firestoreDB.collection("fields").get().addOnSuccessListener {
            val list=ArrayList<String>()
            for (document in it){
                list.add(document.id)
            }
            data.value=list
        }
        return data

    }
    fun getMentor(field:String):MutableLiveData<ArrayList<Users>>{
        val data:MutableLiveData<ArrayList<Users>> = MutableLiveData()
        val temp:ArrayList<Users> = ArrayList()
        firestoreDB.collection("users").get().addOnSuccessListener {
                  for (doc in it){
                      val curr=(doc as DocumentSnapshot).toObject(Users::class.java)
                       if (curr!!.fieldsMentoring==null) continue
                      if(curr.fieldsMentoring!!.contains(field)){
                          temp.add(curr)
                      }
                  }
            Log.i("ankit","${temp}")
            data.value=temp
        }.addOnFailureListener {
           data.value=null
        }
        return data
    }


    fun getuserpr(): MutableLiveData<ArrayList<Users>> {
        val id=auth.currentUser!!.uid
        val data:MutableLiveData<ArrayList<Users>> = MutableLiveData()

        firestoreDB.collection("users").document(id).collection("pendingRequests").whereEqualTo("accepted",-1).get()
            .addOnSuccessListener {
                val temp:ArrayList<Users> = ArrayList()
                val sz=it.size();
                for (doc in it){
                    val uid=doc.id
                    firestoreDB.collection("users").document(uid).get().addOnSuccessListener {
                        it.toObject(Users::class.java)?.let { it1 -> temp.add(it1) }
                        if (temp.size==sz) data.value=temp
                    }
                }
            }
        return data
    }
    fun updateuserpr(userid:String,accepted:Int){
        val id=auth.currentUser!!.uid
        if (accepted==1)
        firestoreDB.collection("users").document(userid).collection("acceptedRequests").document(id).set(
            mapOf(
                "accepted" to 1
            ))

        firestoreDB.collection("users").document(id).collection("pendingRequests").document("userid")
            .update("accepted",accepted).addOnSuccessListener {

          }


    }


}
