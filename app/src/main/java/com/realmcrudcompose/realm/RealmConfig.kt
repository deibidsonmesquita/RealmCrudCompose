package com.realmcrudcompose.realm

import android.app.Application
import android.util.Log
import io.realm.Realm



class RealmConfig: Application(){
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = io.realm.RealmConfiguration.Builder()
            .name("gdmDb.realm")
            .schemaVersion(0)
            //.allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)
        Log.i("Realm DB Location: ", Realm.getDefaultInstance().path)
    }
}