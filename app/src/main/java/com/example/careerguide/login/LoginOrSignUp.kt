package com.example.careerguide.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.careerguide.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login_or_sign_up.*


class LoginOrSignUp : Fragment() {

    lateinit var auth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_or_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.getCurrentUser() != null) {
            view.findNavController().navigate(R.id.homeFragment)
        }

        login_.setOnClickListener {
//            phone=null
//            name=null
            view.findNavController().navigate(R.id.loginFrag)
        }
        signUp_.setOnClickListener {
//            phone=null
//            name=null
            view.findNavController().navigate(R.id.signUpFragment)
        }
    }
}