package com.example.feature_note.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feature_note.R
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.DateFormat


@Entity(tableName = "note_db")
@Parcelize
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val color: Int? = null,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable {
    @IgnoredOnParcel
    val createdDateFormatted: String
        get() = DateFormat.getInstance().format(timestamp)


    companion object {
        val colors = listOf(
            R.color.light_red,
            R.color.light_blue,
            R.color.light_green,
            R.color.light_purple,
            R.color.light_orange
        )
    }
}
