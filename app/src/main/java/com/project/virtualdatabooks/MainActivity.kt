package com.project.virtualdatabooks

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.virtualdatabooks.UI.BiodataFragment
import com.project.virtualdatabooks.UI.DashboardFragment
import com.project.virtualdatabooks.UI.ERaportFragment
import com.project.virtualdatabooks.UI.EditDataFragment
import com.project.virtualdatabooks.UI.StudentDataFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        val userRole = getUserRole()
        if (userRole == "Admin") {
            bottomNavigationView.menu.clear()
            bottomNavigationView.inflateMenu(R.menu.bottom_navigation_admin)
        } else {
            bottomNavigationView.menu.clear()
            bottomNavigationView.inflateMenu(R.menu.bottom_navigation_student)
        }

        if (savedInstanceState == null) {
            replaceFragment(DashboardFragment())
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(DashboardFragment())
                    true
                }
                R.id.navigation_edit_data -> {
                    replaceFragment(EditDataFragment())
                    true
                }
                R.id.navigation_eraport -> {
                    replaceFragment(ERaportFragment())
                    true
                }
                //admin
                R.id.navigation_data_siswa -> {
                    replaceFragment(StudentDataFragment())
                    true
                }
                R.id.navigation_biodata -> {
                    replaceFragment(BiodataFragment())
                    true
                }
            else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getUserRole(): Any {
        return "Admin"
    }
}