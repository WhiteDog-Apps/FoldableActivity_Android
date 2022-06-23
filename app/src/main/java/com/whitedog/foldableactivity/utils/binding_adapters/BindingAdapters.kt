package com.whitedog.foldableactivity.utils.binding_adapters

import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.MaterialToolbar

object BindingAdapters {

    @BindingAdapter("app:onNavigationClick")
    @JvmStatic
    fun onNavigationClick(materialToolbar: MaterialToolbar, action: () -> Unit) {
        materialToolbar.setNavigationOnClickListener {
            action()
        }
    }

}