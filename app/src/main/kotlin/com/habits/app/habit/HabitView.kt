package com.habits.app.habit

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import at.florianschuster.control.bind
import at.florianschuster.control.distinctMap
import com.habits.app.Async
import com.habits.app.R
import com.habits.app.databinding.FragmentHabitBinding
import com.habits.app.ext.viewBinding
import com.habits.app.loading
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HabitView : Fragment(R.layout.fragment_habit) {

    private val args by navArgs<HabitViewArgs>()
    private val binding by viewBinding(FragmentHabitBinding::bind)
    private val viewModel: HabitViewModel by viewModel { parametersOf(args.habitId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.controller.state
            .distinctMap(HabitViewModel.State::habit)
            .bind { asyncHabit ->
                binding.loadingProgressBar.isInvisible = !asyncHabit.loading
                if (asyncHabit is Async.Success) {
                    binding.titleTextView.text = asyncHabit.value.name
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}