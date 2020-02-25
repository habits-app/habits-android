package com.habits.app.overview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import at.florianschuster.control.bind
import at.florianschuster.control.distinctMap
import com.habits.app.R
import com.habits.app.ext.viewBinding
import com.habits.app.databinding.FragmentOverviewBinding
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OverviewView : Fragment(R.layout.fragment_overview) {

    private val binding by viewBinding(FragmentOverviewBinding::bind)
    private val viewModel: OverviewViewModel by viewModel()
    private val adapter: OverviewAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.overviewRecyclerView.adapter = adapter

        adapter.interaction
            .filterIsInstance<OverviewAdapterInteraction.HabitClicked>()
            .map { OverviewViewModel.Action.HabitClicked(it.habit) }
            .bind(to = viewModel.controller::dispatch)
            .launchIn(scope = viewLifecycleOwner.lifecycleScope)

        viewModel.controller.state
            .distinctMap(OverviewViewModel.State::habits)
            .bind(to = adapter::submitList)
            .launchIn(scope = viewLifecycleOwner.lifecycleScope)
    }
}
