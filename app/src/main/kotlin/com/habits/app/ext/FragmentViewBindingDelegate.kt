package com.habits.app.ext

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <B : ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> B
) = FragmentViewBindingDelegate(this, viewBindingFactory)

class FragmentViewBindingDelegate<B : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> B
) : ReadOnlyProperty<Fragment, B> {

    private var _binding: B? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            _binding = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): B {
        val binding = _binding
        if (binding != null) return binding
        val lifecycleState = fragment.viewLifecycleOwner.lifecycle.currentState
        check(lifecycleState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("fragment view is destroyed")
        }
        return viewBindingFactory(thisRef.requireView()).also { _binding = it }
    }
}