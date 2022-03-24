package com.noteapp.notes.core.utils

import java.text.SimpleDateFormat

fun getDateString(time: Long): String {
    val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy")

    return simpleDateFormat.format(time * 1000L)
}