package com.example.feature_note.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.DateFormat



@Parcelize
data class Note(
    val id: String,
    val title: String,
    val content: String,
    val color: String,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable {
    @IgnoredOnParcel
    val createdDateFormatted: String
        get() = DateFormat.getInstance().format(timestamp)

    companion object {
        val colors = listOf(
            "#FFFFAB91",
            "#FF81DEEA",
            "#FFE7ED9B",
            "#FFCF94DA",
            "#FF81DEEA"
        )
    }
}
