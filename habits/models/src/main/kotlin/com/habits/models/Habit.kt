package com.habits.models

data class Habit(
    val id: Long,
    val name: String,
    val entities: Set<EntityPerPeriod>
)

data class EntityPerPeriod(
    val unit: Entity,
    val period: Period,
    val amount: Double = 1.0
)

enum class Period { millisecond, second, minute, hour, day, week, month }

data class Entity(
    val id: Long,
    val name: String,
    val amount: Double = 1.0
)

object ModelTest {

    private val tschick = Entity(0, "Tschick")
    private val tschickProStunde = EntityPerPeriod(tschick, Period.hour)

    private val tschickTeer = Entity(0, "Teer")
    private val tschickTeerProTschick = EntityPerPeriod(tschickTeer, Period.hour)

    private val fleisch = Entity(1, "Fleisch")
    private val fleischProTag = EntityPerPeriod(fleisch, Period.week, amount = 2.0)

    val habits = listOf(
        Habit(0, "Rauchen aufhören", setOf(tschickProStunde, tschickTeerProTschick)),
        Habit(1, "Vegetarisch ernähren", setOf(fleischProTag))
    )
}