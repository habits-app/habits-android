package com.habits.app

import androidx.navigation.NavController
import com.habits.app.habit.HabitViewDirections
import com.habits.models.Habit

sealed class AppRoute {
    object Root : AppRoute()

    data class Back(val popped: (Boolean) -> Unit) : AppRoute()
    data class Up(val successful: (Boolean) -> Unit) : AppRoute()

    data class ShowHabitDetail(val habit: Habit) : AppRoute()
}

class AppCoordinator {

    private var navigator: NavController? = null

    fun attachNavigator(navigator: NavController) {
        this.navigator = navigator
    }

    fun detachNavigator() {
        this.navigator = null
    }

    fun navigate(route: AppRoute) = when (route) {
        is AppRoute.Root -> navigateToRoot()
        is AppRoute.Back -> navigateBack(route)
        is AppRoute.Up -> navigateUp(route)
        is AppRoute.ShowHabitDetail -> showHabitDetail(route)
    }

    private fun navigateToRoot() {
        navigator?.popBackStack(R.id.overview, false)
    }

    private fun navigateBack(route: AppRoute.Back) {
        val successful = navigator?.popBackStack() ?: false
        route.popped(successful)
    }

    private fun navigateUp(route: AppRoute.Up) {
        val navigationSuccessful = navigator?.navigateUp() ?: false
        route.successful(navigationSuccessful)
    }

    private fun showHabitDetail(route: AppRoute.ShowHabitDetail) {
        navigator?.navigate(HabitViewDirections.actionToHabit(route.habit.id))
    }
}