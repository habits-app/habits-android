package com.habits.app.habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.florianschuster.control.Controller
import at.florianschuster.control.Mutator
import at.florianschuster.control.Reducer
import at.florianschuster.control.createController
import com.habits.app.Async
import com.habits.app.async
import com.habits.app.core.DispatcherProvider
import com.habits.models.Habit
import com.habits.models.ModelTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class HabitViewModel(
    private val habitId: Long,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    sealed class Action {
        object InitialLoad : Action()
    }

    sealed class Mutation {
        data class SetHabit(val habit: Async<Habit>) : Mutation()
    }

    data class State(
        val habit: Async<Habit> = Async.Uninitialized
    )

    val controller: Controller<Action, Mutation, State> = viewModelScope.createController(
        dispatcher = dispatcherProvider.default,
        initialState = State(),
        actionsTransformer = { it.onStart { emit(Action.InitialLoad) } },
        mutator = Mutator { action, _, _ ->
            when (action) {
                is Action.InitialLoad -> flow {
                    emit(Mutation.SetHabit(Async.Loading))
                    delay(1000)
                    val habit = async { ModelTest.habits.first { it.id == habitId } }
                    emit(Mutation.SetHabit(habit))
                }
            }
        },
        reducer = Reducer { mutation, previousState ->
            when (mutation) {
                is Mutation.SetHabit -> previousState.copy(habit = mutation.habit)
            }
        }
    )
}