package com.kevinroctavian.mytodolist.di

import android.app.Application
import androidx.room.Room
import com.kevinroctavian.mytodolist.data.database.DaoApp
import com.kevinroctavian.mytodolist.data.database.DatabaseApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Singleton
    @Provides
    fun getDbApp(application: Application) : DatabaseApp =
        Room.databaseBuilder(application , DatabaseApp::class.java , "mytodolist_database").build()

    @Singleton
    @Provides
    fun getDao(dbApp: DatabaseApp) : DaoApp = dbApp.getDaoApp()

}