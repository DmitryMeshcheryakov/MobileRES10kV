package com.graime.streetvoice.screens.base.ui.view

import android.app.DatePickerDialog
import android.net.Uri
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.smartgrid.mobileres10kv.base.models.BSItem

@StateStrategyType(SkipStrategy::class)
interface BaseView : MvpView {

    fun showProgress(show: Boolean) = Unit

    fun showError(message: String) = Unit

    fun showAlert(title: String, message: String) = Unit

    fun showBottomDialog(items: List<BSItem>,
                         itemClickListener: ((id: Int) -> Unit)? = null,
                         title: String? = null
    ) = Unit

    fun showImagePicker(onPickListener: (uri: Uri) -> Unit)

    fun showNotPermitted()

    fun showDialog(title: String, message: String? = null, positiveText: String?, negativeText: String?, positiveClick: (()->Unit)? = null, negativeClick: (()->Unit)? = null)

    fun onBack()
}