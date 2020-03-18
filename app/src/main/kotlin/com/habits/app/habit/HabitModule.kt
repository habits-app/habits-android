package com.habits.app.habit

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val habitModule = module {
    viewModel { (id: Long) -> HabitViewModel(habitId = id, dispatcherProvider = get()) }
}
