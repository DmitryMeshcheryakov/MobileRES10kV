package com.graime.streetvoice.modules.resources

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ResourcesModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideResourceHelper(): ResourcesHelper {
        return ResourcesHelper(context)
    }
}