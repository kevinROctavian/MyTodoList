package com.kevinroctavian.mytodolist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kevinroctavian.mytodolist.data.models.Todo


@Database(entities = [Todo::class] , version = 1 , exportSchema = false)
abstract class DatabaseApp : RoomDatabase() {
    abstract fun getDaoApp() : DaoApp
}