package com.example.movieshiringtask.businesslogic

import androidx.room.Database
import androidx.room.RoomDatabase

const val DATABASE_NAME = "Movie-db"

@Database(entities = [Search::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun roomDao(): RoomDao
}