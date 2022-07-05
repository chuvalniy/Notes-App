package com.example.common.utils

import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StateStringPropertyDelegate(
    private val state: SavedStateHandle,
    private val key: String,
    savedValue: String? = null,
    initialValue: String,
) : ReadWriteProperty<Any, String> {

    private var currentValue = state.get<String>(key) ?: savedValue ?: initialValue

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return currentValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        currentValue = value
        state.set(key, value)
    }
}