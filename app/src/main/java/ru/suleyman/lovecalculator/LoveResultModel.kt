package ru.suleyman.lovecalculator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoveResultModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fname: String,
    val sname: String,
    val percentage: Int,
    val result: String
)
