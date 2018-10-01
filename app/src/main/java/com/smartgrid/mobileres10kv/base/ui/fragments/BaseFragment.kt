package com.graime.streetvoice.screens.base.ui.fragments

import android.net.Uri
import android.support.design.widget.BottomSheetDialog
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.graime.streetvoice.common.utils.Utils
import com.graime.streetvoice.screens.base.ui.view.BaseView
import com.smartgrid.mobileres10kv.R
import com.smartgrid.mobileres10kv.base.models.BSItem


abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    abstract fun tag(): String

    open fun title(): Int = -1

 //   private var progress: ProgressDlg? = null

    protected val hideErrorTextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    override fun onBack() {
        fragmentManager?.popBackStack()
    }

    fun loadImage(imageView: ImageView?, uri: String?, pleloadBg: Boolean = true) {
//        if (!uri.isNullOrEmpty())
//            imageView?.let {
//                GlideApp.with(it)
//                        .load(Utils.configureImageUrl(uri))
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .skipMemoryCache(true)
//                        .into(it)
//                        .onLoadStarted(if (pleloadBg) ContextCompat.getDrawable(it.context, R.drawable.preload_bg) else null)
//            }
    }

    override fun showProgress(show: Boolean) {
//        context?.let {
//            if (progress == null)
//                progress = ProgressDlg(activity)
//            if (show && !isHidden) progress?.show()
//            else progress?.hide()
//        }
    }

    override fun showError(message: String) {

    }

    override fun showAlert(title: String, message: String) {

    }

    override fun showBottomDialog(items: List<BSItem>,
                                  itemClickListener: ((id: Int) -> Unit)?,
                                  title: String?) {
        fragmentManager?.let {
//            BottomSheetDialog.builder(it)
//                    .setItems(items)
//                    .setListener(itemClickListener)
//                    .setTitle(title)
//                    .show()
        }
    }

    override fun showDialog(title: String, message: String?, positiveText: String?, negativeText: String?, positiveClick: (()->Unit)?, negativeClick: (()->Unit)?) {
        fragmentManager?.let { fragmentManager ->
//            PopupDialog.builder(fragmentManager)
//                    .setCancelable(true)
//                    .setTitle(title)
//                    .setText(message)
//                    .setPositiveText(positiveText)
//                    .setNegativeText(negativeText)
//                    .setPositiveClickListener { positiveClick?.invoke() }
//                    .setNegativeClickListener { negativeClick?.invoke() }
//                    .show()
        }
    }


    override fun showNotPermitted() {
//        showDialog(
//                title = getString(R.string.permission_denied),
//                message = getString(R.string.permissoin_denied_description),
//                negativeText = getString(R.string.cancel),
//                positiveText = getString(R.string.go_to_permissions),
//                positiveClick = {
//                    IntentHelper.openAppSettings(activity)
//                }
//        )
    }

    override fun showImagePicker(onPickListener: (uri: Uri) -> Unit) {

    }
}