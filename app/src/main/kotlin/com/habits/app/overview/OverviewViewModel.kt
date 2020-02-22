package com.habits.app.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.florianschuster.control.Controller
import at.florianschuster.control.createController
import com.habits.app.AppCoordinator
import com.habits.app.AppRoute
import com.habits.app.core.DispatcherProvider
import com.habits.models.Habit
import com.habits.models.ModelTest
import kotlinx.coroutines.flow.flow

internal class OverviewViewModel(
    coordinator: AppCoordinator,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    sealed class Action {
        data class HabitClicked(val habit: Habit) : Action()
    }

    sealed class Mutation

    data class State(
        val habits: List<Habit> = ModelTest.habits
    )

    val controller: Controller<Action, Mutation, State> = viewModelScope.createController(
        initialState = State(),
        dispatcher = dispatcherProvider.default,
        mutator = { action, _ ->
            when (action) {
                is Action.HabitClicked -> flow {
                    val route = AppRoute.ShowHabitDetail(action.habit)
                    coordinator.navigate(route)
                }
            }
        }
    )
}