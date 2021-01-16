package com.satya.pretest

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.satya.data.database.AnimeDatabase

class PreTestApplication : MultiDexApplication() {

    companion object {
        lateinit var database: AnimeDatabase
        lateinit var instance: PreTestApplication
    }

    init {
        instance = this
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        database = AnimeDatabase.invoke(this);
    }
}