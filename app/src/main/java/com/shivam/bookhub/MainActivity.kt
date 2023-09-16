package com.shivam.bookhub

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var drawer: DrawerLayout
    private lateinit var frameLayout: FrameLayout
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navigationBar: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationBar = findViewById(R.id.nav)
        frameLayout = findViewById(R.id.frame)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        navigationBar = findViewById(R.id.nav)
        toolbar = findViewById(R.id.toolbar)
        drawer = findViewById(R.id.drawerLayout)

        supportFragmentManager.beginTransaction().replace(R.id.frame, DashboardFragment()).commit()


        setSupportActionBar(toolbar)
       // supportActionBar?.title = "Welcome"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // thia below line shows where hamburger icons show
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawer,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer.addDrawerListener(actionBarDrawerToggle)

        // this line toogle+navigation drawer state ko sync krege
        actionBarDrawerToggle.syncState()
        navigationBar.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> {
                    Toast.makeText(this@MainActivity, "Home", Toast.LENGTH_SHORT)
                        .show()
                    supportFragmentManager.beginTransaction().addToBackStack("Dashboard")
                        .replace(R.id.frame, DashboardFragment()).commit()
                    supportActionBar?.title = "Dashboard"
                }
                R.id.favourites -> {
                    Toast.makeText(
                        this@MainActivity,
                        "Favourite",
                        Toast.LENGTH_SHORT
                    ).show()
                    supportFragmentManager.beginTransaction().addToBackStack("Favourites")
                        .replace(R.id.frame, FavouritesFragment()).commit()
                    supportActionBar?.title = "Favourites"
                }
                R.id.profile -> {
                    Toast.makeText(this@MainActivity, "Profile", Toast.LENGTH_SHORT)
                        .show()
                    supportFragmentManager.beginTransaction().addToBackStack("Profile")
                        .replace(R.id.frame, ProfileFragment()).commit()
                    supportActionBar?.title = "Profile"

                }
                R.id.about -> {
                    Toast.makeText(this@MainActivity, "About", Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction().addToBackStack("About")
                        .replace(R.id.frame, AboutUsFragment()).commit()
                    supportActionBar?.title = "About"
                }
            }
            drawer.closeDrawers()

            return@setNavigationItemSelectedListener true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }


}