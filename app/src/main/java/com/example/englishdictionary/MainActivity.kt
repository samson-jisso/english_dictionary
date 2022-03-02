package com.example.englishdictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.englishdictionary.databinding.*
import com.example.englishdictionary.favorite.BookmarkFragment
import com.example.englishdictionary.history.HistoryFragment
import com.example.englishdictionary.home.HomeFragment
import com.google.android.material.navigation.NavigationView

lateinit var toggle: ActionBarDrawerToggle

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val navView: NavigationView = binding.navView
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.history -> changeFragment(R.id.historyFragment)
                R.id.favorite -> changeFragment(R.id.favoriteFragment)
                else -> changeFragment(R.id.homeFragment)

            }
            true
        }
        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeFragment(fragment: Int) {
        findNavController(R.id.nav_host_fragment).navigate(fragment)
        drawerLayout.close()
    }
}
//        val bottomNavView = binding.bottomNav
//        bottomNavView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.detail -> {
//                    setThatFragment(detailFragment)
//                }
//                R.id.bookmark -> {
//                    setThatFragment(bookmarkFragment)
//                }
//                R.id.history -> {
//                    setThatFragment(historyFragment)
//                }
//                else -> {
//                    setThatFragment(homeFragment)
//                }
//            }
//            true
//        }
//    }
//
//    private fun setThatFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.nav_host_fragment, fragment)
//            commit()
//        }
//    }