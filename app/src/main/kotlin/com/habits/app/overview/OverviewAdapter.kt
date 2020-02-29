package com.habits.app.overview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.habits.app.databinding.ItemOverviewBinding
import com.habits.app.ext.viewBinding
import com.habits.models.Habit
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

internal sealed class OverviewAdapterInteraction {
    data class HabitClicked(val habit: Habit) : OverviewAdapterInteraction()
}

internal class OverviewAdapter : ListAdapter<Habit, HabitViewHolder>(diffUtilItemCallback) {
    private val _interaction = BroadcastChannel<OverviewAdapterInteraction>(1)
    val interaction: Flow<OverviewAdapterInteraction> get() = _interaction.asFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder =
        HabitViewHolder(parent.viewBinding { ItemOverviewBinding.inflate(it, parent, false) })

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) =
        holder.bind(getItem(position)) { _interaction.offer(it) }

    companion object {
        private val diffUtilItemCallback = object : DiffUtil.ItemCallback<Habit>() {
            override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean =
                oldItem == newItem
        }
    }
}

internal class HabitViewHolder(
    private val binding: ItemOverviewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(habit: Habit, interaction: (OverviewAdapterInteraction) -> Unit) {
        binding.root.setOnClickListener { interaction(OverviewAdapterInteraction.HabitClicked(habit)) }
        binding.itemOverViewTitle.text = habit.toString()
    }
}