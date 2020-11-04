package com.example.events

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.events.ui.main.EventsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal val fragments = listOf(
        EventsFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigationViewListener()
        mainBottomNavigationView.selectedItemId = R.id.navigation_home
    }

    private fun initBottomNavigationViewListener() {
        mainBottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> setFragment(0)
                R.id.navigation_close -> finish()
            }
            true
        }
    }

    private fun setFragment(tabOrder: Int) {
        if (tabOrder < fragments.size) {
            val fragment = fragments[tabOrder]
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainFragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}