package com.habits.app

sealed class Async<out Type> {
    open operator fun invoke(): Type? = null

    object Uninitialized : Async<Nothing>()
    object Loading : Async<Nothing>()
    data class Failure(val error: Throwable) : Async<Nothing>()
    data class Success<Type>(val value: Type) : Async<Type>() {
        override fun invoke(): Type = value
    }
}

inline fun <Type> async(
    block: () -> Type
): Async<Type> = runCatching { block() }.fold({ Async.Success(it) }, { Async.Failure(it) })

suspend inline fun <Type> loadAsync(
    crossinline block: suspend () -> Type
): Async<Type> = runCatching { block() }.fold({ Async.Success(it) }, { Async.Failure(it) })

val Async<*>.loading: Boolean get() = this is Async.Loading