package com.example.careerguide.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.careerguide.MainActivity
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit


class LoginFrag : Fragment() {

    lateinit var auth : FirebaseAuth
    private lateinit var loginmvvm: loginViewModel

    companion object {
        var phone: String? = null
        var VerificationId: String? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth= FirebaseAuth.getInstance()

        loginmvvm= ViewModelProvider(requireActivity()).get(loginViewModel::class.java)



//        (activity as MainActivity).setVisibleNav()
        button_send_otp.setOnClickListener {

            hideKeyboard(requireActivity())

            phone=edit_phone.text.toString().trim()
            if(phone!!.length!=10)
            {
                edit_phone.error="Enter valid number"
                return@setOnClickListener
            }
            loginmvvm.UserExist(phone!!)
            loginmvvm.mUserExist.observe(requireActivity(), Observer {
                if (it == true) {

                    sendOtp("+91"+phone!!)
                    view.findNavController().navigate(R.id.otpFragment)

                } else {
                    Toast.makeText(
                            requireActivity(),
                            "User Doesn't Exist Please " +
                                    "Sign Up!!",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            })


            }

//        button_login.setOnClickListener {
//            if(edit_otp.text!!.length!=6)
//            {
//                edit_otp.error="Please enter correct otp"
//                return@setOnClickListener
//            }
//            verifyotp(edit_otp.text.toString())
//        }
    }

    private fun sendOtp(phone: String){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            //automatic verification
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {

//                    verifyotp(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Toast.makeText(requireActivity(), e.message!!, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }

            //code sent to device but no auto verification
            override fun onCodeSent(
                verificationId: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, forceResendingToken)
                VerificationId = verificationId
                Log.i("otp",VerificationId.toString())
            }
        }



    fun hideKeyboard(activity: Activity) {

        val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if no view has focus
        val currentFocusedView = activity.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                    currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}