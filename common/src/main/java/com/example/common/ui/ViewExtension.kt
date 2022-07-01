package com.example.common.ui

import android.widget.SearchView

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(query: String?): Boolean {
            listener(query.orEmpty())
            return true
        }
    })
}

