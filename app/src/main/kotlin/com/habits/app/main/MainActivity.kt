package com.habits.app.main

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.habits.app.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navController: NavController by lazy { findNavController(R.id.navHost) }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()

    override fun onBackPressed() {
        if (!navController.popBackStack()) super.onBackPressed()
    }
}
