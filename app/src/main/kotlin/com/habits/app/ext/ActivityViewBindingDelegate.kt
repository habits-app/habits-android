package com.habits.app.ext

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

inline fun <B : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> B
) = lazy(LazyThreadSafetyMode.NONE) { bindingInflater(layoutInflater) }