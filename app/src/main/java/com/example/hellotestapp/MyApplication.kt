package com.example.hellotestapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
//        Stetho.initialize(
//            Stetho.newInitializerBuilder(this)
//                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
//                .build());
    }
}