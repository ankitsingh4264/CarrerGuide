package com.example.careerguide.Home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.careerguide.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(),NavigationView.OnNavigationItemSelectedListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    lateinit var toggle: ActionBarDrawerToggle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toggle= ActionBarDrawerToggle(
            requireActivity(),
            drawerlayout,
            R.string.open,
            R.string.close
        )
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        
//        navigation.setNavigationItemSelectedListener(requireContext())



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
               Toast.makeText(requireActivity(),"df",Toast.LENGTH_SHORT).show()
            }
        }
        drawerlayout.closeDrawer(GravityCompat.START)
        return true;


    }


}





