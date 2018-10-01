package com.graime.streetvoice.modules.preferences

import android.content.Context
import com.smartgrid.mobileres10kv.common.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun providePreferences(): PreferencesHelper {
        return PreferencesHelper(context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE))
    }
}