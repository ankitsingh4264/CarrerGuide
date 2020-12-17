package com.example.careerguide

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.drawerlayout

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
//        navigation.setupWithNavController(navController)

        navigation.setNavigationItemSelectedListener { item: MenuItem ->
            Log.i("ankit","rajeev")
            when(item.itemId){
                R.id.nav_profile -> {
                    Log.i("ankit","rajeev")
                    navController.navigate(R.id.loginFrag)
                }
            }
            drawerlayout.closeDrawer(GravityCompat.START)
            navigation.setCheckedItem(item.itemId)
            true
        }

    }
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.nav_profile -> {
//
//                Log.i("ra","dgs")
//            }
//
//        }
//
//        return true;
//
//
//    }
    fun setVisibleNav(){
//    drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
    drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }


}