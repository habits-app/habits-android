package com.habits.app.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.florianschuster.control.Controller
import at.florianschuster.control.createController
import com.habits.app.core.DispatcherProvider
import com.habits.models.Habit
import com.habits.models.ModelTest

internal class OverviewViewModel(
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    sealed class Action

    sealed class Mutation

    data class State(
        val habits: List<Habit> = ModelTest.habits
    )

    val controller: Controller<Action, Mutation, State> = viewModelScope.createController(
        initialState = State(),
        dispatcher = dispatcherProvider.default
    )
}