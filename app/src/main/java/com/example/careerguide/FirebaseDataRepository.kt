package com.example.careerguide

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.careerguide.beans.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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

    fun getUserDetails(): MutableLiveData<Users> {
        val userId = auth.currentUser!!.uid
        val userDetailsLiveDate = MutableLiveData<Users>()

        val docRef = firestoreDB.collection("users").document(userId)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: ${snapshot.data}")
                userDetailsLiveDate.value=snapshot.toObject(Users::class.java)
            }
        }

        return userDetailsLiveDate
    }

    fun userUpload(user: Users) : MutableLiveData<Boolean> {
        val id = auth.currentUser!!.uid
        var pos:MutableLiveData<Boolean> = MutableLiveData()

        firestoreDB.collection("users").document(id).set(user).addOnSuccessListener {
            pos.value=true
        }.addOnFailureListener{
            pos.value=false
        }
        return pos
    }

    fun uploadProfileToFirebase(imageURI: Uri?, path: String) {
        val userId = auth.currentUser!!.uid

        val storageReference =
                FirebaseStorage.getInstance().getReference("/UserProfilePicture/$userId/$path")


        storageReference.putFile(imageURI!!).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener {

                firestoreDB.collection("users").document(userId)
                        .update(
                                mapOf(
                                        path to it.toString()
                                        )
                        )
            }
        }
    }

}
