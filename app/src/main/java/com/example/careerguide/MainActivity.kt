package com.example.careerguide

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.careerguide.Profile.profilemvvm
import com.example.careerguide.beans.Users
import com.example.careerguide.login.LoginFrag.Companion.phone
import com.example.careerguide.login.SignUpFragment.Companion.name
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.drawerlayout
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.header.*

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        var mUserDetail: MutableLiveData<Users> = MutableLiveData()
//        mUserDetail=FirebaseDataRepository().getUserDetails()
//        mUserDetail.observe(
//            this
//        ) {
//            //text_nav_name.setText(it.name)
//            text_nav_phone.setText(it.phone)
//        }


        toggle=  ActionBarDrawerToggle(
                this,
                drawerlayout,
                R.string.open,
                R.string.close
        )

        drawerlayout.addDrawerListener(toggle)
        drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toggle.syncState()
        val navController = findNavController(R.id.nav_main_fragment)





        navigation.setNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId){
                R.id.nav_profile -> {
                    //Log.i("ankit","rajeev")
                    navController.navigate(R.id.profile)
                }
                R.id.nav_find_mentor->{
//                    setinVisibleNav()
                    navController.navigate(R.id.findMentorFragment)
                }
                R.id.nav_pr->{
                    setinVisibleNav()
                    navController.navigate(R.id.pendingRequestFragment)
                }
            }
            drawerlayout.closeDrawer(GravityCompat.START)
            navigation.setCheckedItem(item.itemId)
            true
        }

    }


    fun setVisibleNav(){
    drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
    fun setinVisibleNav(){
        drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }


}