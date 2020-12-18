package com.example.careerguide.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.careerguide.R
import com.example.careerguide.login.LoginFrag.Companion.phone
import com.example.careerguide.login.SignUpFragment.Companion.name
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login_or_sign_up.*

class LoginOrSignUp : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_or_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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