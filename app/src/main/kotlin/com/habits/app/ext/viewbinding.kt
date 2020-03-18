package com.habits.app.ext

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <B : ViewBinding> View.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> B
): B = bindingInflater(LayoutInflater.from(context))

inline fun <B : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> B
): Lazy<B> = lazy { bindingInflater(layoutInflater) }

fun <B : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> B
): ReadOnlyProperty<Fragment, B> = object : ReadOnlyProperty<Fragment, B> {

    private var binding: B? = null

    init {
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                viewLifecycleOwnerLiveData.observe(this@viewBinding) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): B {
        val binding = binding
        if (binding != null) return binding
        val lifecycleState = viewLifecycleOwner.lifecycle.currentState
        check(lifecycleState.isAtLeast(Lifecycle.State.INITIALIZED)) { "fragment view is destroyed" }
        return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
    }
}