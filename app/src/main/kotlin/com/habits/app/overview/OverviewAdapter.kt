package com.habits.app.overview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.habits.app.R
import com.habits.app.ext.inflate
import com.habits.models.Habit
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_overview.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

sealed class OverviewAdapterInteraction {
    data class HabitClicked(val habit: Habit) : OverviewAdapterInteraction()
}

internal class OverviewAdapter : ListAdapter<Habit, HabitViewHolder>(diffUtilItemCallback) {
    private val _interaction = BroadcastChannel<OverviewAdapterInteraction>(1)
    val interaction: Flow<OverviewAdapterInteraction> get() = _interaction.asFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder =
        HabitViewHolder(parent.inflate(R.layout.item_overview))

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
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(habit: Habit, interaction: (OverviewAdapterInteraction) -> Unit) {
        containerView.setOnClickListener {
            interaction(OverviewAdapterInteraction.HabitClicked(habit))
        }
        itemOverViewTitle.text = habit.toString()
    }
}