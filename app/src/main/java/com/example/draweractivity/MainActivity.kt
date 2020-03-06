package com.example.draweractivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_home.*

class MainActivity : AppCompatActivity() {


    private lateinit var toggle: ActionBarDrawerToggle
    var currentFragment = DrawerFragment.HOME

    enum class DrawerFragment(val fragment: Fragment) {
        HOME(HomeFragment.newInstance()),
        SECOND(SecondFragment.newInstance())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(
            R.id.frame_container,
            HomeFragment(),
            HomeFragment().javaClass.simpleName
        ).commit()
        toggle = ActionBarDrawerToggle(this, activity_main,tbLanding, R.string.Open, R.string.Close)
        activity_main.addDrawerListener(toggle)
        toggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        toggle.isDrawerIndicatorEnabled = true


        getActionBar()?.elevation = 0f
        nv.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                val id = item.getItemId()
                when (id) {
                    R.id.account -> {
                        activity_main.closeDrawer(GravityCompat.START)
                        addNavFragment(DrawerFragment.HOME)
                    }
                    R.id.settings -> {
                        activity_main.closeDrawer(GravityCompat.START)
                        addNavFragment(DrawerFragment.SECOND)
                    }
                    R.id.mycart -> {
                        activity_main.closeDrawer(GravityCompat.START)
                        Toast.makeText(this@MainActivity, "My Cart", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> return true
                }
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    private fun addNavFragment(drawerFragment: DrawerFragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.hide(currentFragment.fragment)
        if (drawerFragment.fragment.isAdded) {
            fragmentTransaction.show(drawerFragment.fragment)
        } else {
            fragmentTransaction.add(
                R.id.frame_container,
                drawerFragment.fragment,
                drawerFragment.fragment.javaClass.simpleName
            )
        }
        fragmentTransaction.commit()
        currentFragment = drawerFragment
    }


}
