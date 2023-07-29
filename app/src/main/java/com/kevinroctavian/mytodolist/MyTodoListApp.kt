package com.kevinroctavian.mytodolist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyTodoListApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}