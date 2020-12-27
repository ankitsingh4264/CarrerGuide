package com.example.careerguide.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.careerguide.R
import com.example.careerguide.Utils
import com.example.careerguide.login.LoginFrag.Companion.VerificationId
import com.example.careerguide.login.LoginFrag.Companion.phone
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.concurrent.TimeUnit

class SignUpFragment : Fragment() {
    lateinit var auth : FirebaseAuth
    private lateinit var sinUpmvvm: signUpViewModel

    companion object {
        var name:String? =null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth= FirebaseAuth.getInstance()

        sinUpmvvm= ViewModelProvider(requireActivity()).get(signUpViewModel::class.java)

        button_signUp_otp.setOnClickListener {
            hideKeyboard(requireActivity())
            phone =edit_phone_no.text.toString().trim()
            name=edit_name.text.toString()
            sinUpmvvm.userExist(phone!!)
            sinUpmvvm.mUserExist.observe(requireActivity(), Observer {
                if (it == true) {
                    Toast.makeText(
                            requireActivity(),
                            "User Already Exist Please " +
                                    "Login!!",
                            Toast.LENGTH_SHORT
                    ).show()

                } else {

                    if(phone!!.length!=10)
                    {
                        edit_phone.error="Enter valid number"
                        return@Observer
                    }
                    else if(name==null)
                    {
                        edit_name.error="Enter name"
                        return@Observer
                    }
                    else
                    {    Utils.fromsignup=true
                        sendOtp("+91"+ phone!!)
                        view.findNavController().navigate(R.id.otpFragment)
                    }
                }
            })
        }


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
//                    Log.i("otp", LoginFrag.VerificationId.toString())
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