package com.example.englishdictionary

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.englishdictionary.databinding.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(this.layoutInflater)
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
                else -> changeFragment(R.id.detailFragment)

            }
            true
        }

        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            hideKeyboard()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeFragment(fragment: Int) {
        findNavController(R.id.nav_host_fragment).navigate(fragment)
        drawerLayout.close()
    }

    override fun onBackPressed() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Exit")
        alertBuilder.setIcon(R.drawable.ic_warning)
        alertBuilder.setMessage("Are you sure to exit?")
        alertBuilder.setPositiveButton("Yes"){
            Dialog,which ->
            finish()
            super.onBackPressed()
        }
        alertBuilder.setNegativeButton("No") {
            Dialog,which ->
            Toast.makeText(this,"have fun",Toast.LENGTH_SHORT).show()
        }
        val createBuild = alertBuilder.create()
        createBuild.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    fun hideKeyboard() {
        val view = this.currentFocus
        if(view != null){
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}