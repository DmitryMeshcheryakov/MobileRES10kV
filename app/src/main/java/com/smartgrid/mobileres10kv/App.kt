package com.smartgrid.mobileres10kv

import android.app.Application
import com.glidebitmappool.GlideBitmapPool
import com.graime.streetvoice.common.utils.AndroidBus
import com.graime.streetvoice.modules.AppComponent
import com.graime.streetvoice.modules.AppModule
import com.graime.streetvoice.modules.DaggerAppComponent
import com.graime.streetvoice.modules.location.LocationModule
import com.graime.streetvoice.modules.preferences.PreferencesModule
import com.graime.streetvoice.modules.resources.ResourcesModule

class App : Application(){

    companion object {

        var component: AppComponent? = null
            private set

        lateinit var bus: AndroidBus

    }

    override fun onCreate() {
        super.onCreate()
        bus = AndroidBus()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .preferencesModule(PreferencesModule(this))
                .resourcesModule(ResourcesModule(this))
                .locationModule(LocationModule(this))
                .build()
    }
}