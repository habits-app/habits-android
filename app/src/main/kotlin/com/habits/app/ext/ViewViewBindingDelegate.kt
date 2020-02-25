package com.habits.app.ext

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding

inline fun <B : ViewBinding> View.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> B
) = bindingInflater(LayoutInflater.from(context))