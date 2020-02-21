package com.habits.app.overview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import at.florianschuster.control.bind
import at.florianschuster.control.distinctMap
import com.habits.app.R
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.coroutines.flow.launchIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class OverviewView : Fragment(R.layout.fragment_overview) {

    private val viewModel: OverviewViewModel by viewModel()
    private val navController: NavController by lazy(::findNavController)

    private val adapter = OverviewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        overviewRecyclerView.adapter = adapter

        viewModel.controller.state
            .distinctMap(OverviewViewModel.State::habits)
            .bind(to = adapter::submitList)
            .launchIn(scope = viewLifecycleOwner.lifecycleScope)
    }
}
