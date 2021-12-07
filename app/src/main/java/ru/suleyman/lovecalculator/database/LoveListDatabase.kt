package ru.suleyman.lovecalculator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.suleyman.lovecalculator.LoveResultModel

@Database(entities = [LoveResultModel::class], version = 1)
abstract class LoveListDatabase: RoomDatabase() {

    abstract fun loveListDao(): LoveListDao

}