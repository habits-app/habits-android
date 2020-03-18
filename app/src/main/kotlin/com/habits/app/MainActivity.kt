package com.habits.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val coordinator: AppCoordinator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coordinator.attachNavigator(
            (supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment).navController
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        coordinator.detachNavigator()
    }

    override fun onSupportNavigateUp(): Boolean {
        var success = false
        coordinator.navigate(AppRoute.Up { success = it })
        return success
    }

    override fun onBackPressed() {
        val route = AppRoute.Back { popped ->
            if (!popped) super.onBackPressed()
        }
        coordinator.navigate(route)
    }
}