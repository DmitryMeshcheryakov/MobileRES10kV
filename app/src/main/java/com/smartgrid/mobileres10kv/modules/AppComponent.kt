package com.graime.streetvoice.modules

import android.content.Context
import com.graime.streetvoice.modules.location.LocationHelper
import com.graime.streetvoice.modules.location.LocationModule
import com.graime.streetvoice.modules.preferences.PreferencesHelper
import com.graime.streetvoice.modules.preferences.PreferencesModule
import com.graime.streetvoice.modules.resources.ResourcesHelper
import com.graime.streetvoice.modules.resources.ResourcesModule
import com.graime.streetvoice.screens.base.presenter.BasePresenter
import com.graime.streetvoice.screens.base.ui.view.BaseView
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (ResourcesModule::class),
    (PreferencesModule::class),
    (AppModule::class),
    (LocationModule::class)])
interface AppComponent {

    var resources: ResourcesHelper

    var preferences: PreferencesHelper

    var context: Context

    var location: LocationHelper

    fun inject(basePresenter: BasePresenter<BaseView>)
}