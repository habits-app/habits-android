package com.habits.app.overview

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val detailModule = module {
    viewModel { OverviewViewModel(dispatcherProvider = get()) }
}
