package ru.suleyman.lovecalculator.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.suleyman.lovecalculator.database.LoveListDao
import ru.suleyman.lovecalculator.database.LoveListDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LoveListDatabase {
        return Room.databaseBuilder(context, LoveListDatabase::class.java, "lovelist_db").build()
    }

    @Provides
    @Singleton
    fun provideListDao(loveListDatabase: LoveListDatabase): LoveListDao {
        return loveListDatabase.loveListDao()
    }

}