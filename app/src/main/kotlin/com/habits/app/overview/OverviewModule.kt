package com.habits.app.overview

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val overviewModule = module {
    viewModel { OverviewViewModel(coordinator = get(), dispatcherProvider = get()) }
    factory { OverviewAdapter() }
}
