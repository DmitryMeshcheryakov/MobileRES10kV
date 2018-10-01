package com.graime.streetvoice.modules.location

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class LocationModule (val context: Context) {

    @Provides
    @Singleton
    fun provideLocation(): LocationHelper {
        return LocationHelper(context)
    }
}