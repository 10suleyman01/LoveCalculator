package ru.suleyman.lovecalculator.database

import androidx.room.*
import ru.suleyman.lovecalculator.LoveResultModel

@Dao
interface LoveListDao {

    @Query("SELECT * FROM loveresultmodel")
    suspend fun getAllLoveHistory(): List<LoveResultModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(loveResultModel: LoveResultModel)

    @Query("SELECT EXISTS(SELECT * FROM loveresultmodel WHERE sname = :sname AND fname = :fname)")
    suspend fun isExists(sname: String, fname: String): Boolean

    @Delete
    suspend fun delete(loveResultModel: LoveResultModel)
}