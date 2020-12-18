package com.example.careerguide.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.careerguide.R
import com.example.careerguide.beans.Users
import com.example.careerguide.login.LoginFrag.Companion.VerificationId
import com.example.careerguide.login.LoginFrag.Companion.phone
import com.example.careerguide.login.SignUpFragment.Companion.name
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_otp.*


class OtpFragment : Fragment() {

    private lateinit var otpmvvm: otpViewModel
    lateinit var auth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth= FirebaseAuth.getInstance()

        otpmvvm= ViewModelProvider(requireActivity()).get(otpViewModel::class.java)

        button_verify_otp.setOnClickListener {
            if(edit_otp.text!!.length!=6)
            {
                edit_otp.error="Please enter correct otp"
                return@setOnClickListener
            }
            verifyotp(edit_otp.text.toString())
        }

    }

    private fun verifyotp(otp: String){
        val credential = PhoneAuthProvider.getCredential(VerificationId!!, otp)

        otpmvvm.signInWithPhone(credential, Users(name,phone,true))

        otpmvvm.mSignin.observe(requireActivity(),
            Observer {


                if (it) {
                    Toast.makeText(
                        requireActivity(),
                        "WELCOME",
                        Toast.LENGTH_SHORT
                    ).show()

                    view?.findNavController()?.navigate(R.id.homeFragment)
                } else {
                    Toast.makeText(requireActivity(), "Something Went Wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }



}
