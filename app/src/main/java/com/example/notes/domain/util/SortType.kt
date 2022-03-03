package com.example.notes.domain.util

sealed class SortType {
    object Descending: SortType()
    object Ascending: SortType()
}
