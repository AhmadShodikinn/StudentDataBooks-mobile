package com.project.virtualdatabooks

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.virtualdatabooks.UI.BiodataFragment
import com.project.virtualdatabooks.UI.DashboardFragment
import com.project.virtualdatabooks.UI.ERaportAdminFragment
import com.project.virtualdatabooks.UI.ERaportFragment
import com.project.virtualdatabooks.UI.EditDataAdminFragment
import com.project.virtualdatabooks.UI.EditDataFragment
import com.project.virtualdatabooks.UI.StudentDataFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var activeFragment: Fragment? = null

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

        val isAdmin = intent.extras?.getBoolean("IS_ADMIN")
        Log.d("MainAct", "isAdmin: $isAdmin")

        if (isAdmin == true) {
            bottomNavigationView.menu.clear()
            bottomNavigationView.inflateMenu(R.menu.bottom_navigation_admin)
        } else {
            bottomNavigationView.menu.clear()
            bottomNavigationView.inflateMenu(R.menu.bottom_navigation_student)
        }

        if (savedInstanceState == null) {
            replaceFragment(DashboardFragment())
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(DashboardFragment())
                    true
                }
                R.id.navigation_edit_data -> {
                    val userId = intent.getIntExtra("USER_ID", 0)
                    replaceFragment(EditDataFragment(), userId)
                    true
                }
                R.id.navigation_edit_data_admin -> {
                    replaceFragment(EditDataAdminFragment())
                    true
                }
                R.id.navigation_eraport -> {
                    replaceFragment(ERaportFragment())
                    true
                }
                R.id.navigation_eraport_admin -> {
                    replaceFragment(ERaportAdminFragment())
                    true
                }
                R.id.navigation_data_siswa -> {
                    replaceFragment(StudentDataFragment())
                    true
                }
                R.id.navigation_biodata -> {
                    val userId = intent.getIntExtra("USER_ID", 0)
                    replaceFragment(BiodataFragment(), userId)
                    true
                }
            else -> false
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (activeFragment is DashboardFragment) {
                    finishAffinity()
                } else {
                    replaceFragment(DashboardFragment())
                    bottomNavigationView.selectedItemId = R.id.navigation_home
                }
            }
        })
    }

    private fun replaceFragment(fragment: Fragment, userId: Int? = null) {
        val bundle = Bundle()
        if (userId != null) {
            bundle.putInt("USER_ID", userId)
            fragment.arguments = bundle
        }
        val transaction = supportFragmentManager.beginTransaction()
        activeFragment = fragment
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()

    }

}