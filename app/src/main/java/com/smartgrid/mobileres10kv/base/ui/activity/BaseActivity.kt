package com.graime.streetvoice.screens.base.ui.activity

import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.arellomobile.mvp.MvpAppCompatActivity
import com.graime.streetvoice.common.utils.Utils
import com.graime.streetvoice.screens.base.ui.fragments.BaseFragment
import com.graime.streetvoice.screens.base.ui.view.BaseView
import com.smartgrid.mobileres10kv.R
import com.smartgrid.mobileres10kv.base.models.BSItem

abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    protected fun fragmentTransaction(): FragmentTransaction = supportFragmentManager.beginTransaction()

    override fun showProgress(show: Boolean) {
//        findViewById<View>(R.id.progressBar)?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showError(message: String) {}

    override fun showAlert(title: String, message: String) {}

    override fun showBottomDialog(items: List<BSItem>,
                                  itemClickListener: ((id: Int) -> Unit)?,
                                  title: String?) {}

    override fun showImagePicker(onPickListener: (uri: Uri) -> Unit) {}

    override fun showNotPermitted() {}

    override fun showDialog(title: String, message: String?, positiveText: String?, negativeText: String?, positiveClick: (() -> Unit)?, negativeClick: (() -> Unit)?) {}

    override fun onBack() {
        //finish()
    }

    fun addFragment(fragment: BaseFragment) : Boolean {
        val fr = supportFragmentManager.findFragmentByTag(fragment.tag())
        if (fr != null && fr.isVisible)
            return false

        fragmentTransaction().addWithAddBackStack(fragment, fragment.tag())
        Utils.hideSoftKeyboard(this)
        return true
    }

    fun replaceFragment(fragment: BaseFragment) : Boolean {
        val fr = supportFragmentManager.findFragmentByTag(fragment.tag())
        if (fr != null && fr.isVisible)
            return false

        fragmentTransaction().replaceWithSlideInBottom(fragment, fragment.tag())
        Utils.hideSoftKeyboard(this)
        return true
    }
}

fun FragmentTransaction.slideBottom() {
    setCustomAnimations(R.animator.slide_bottom_up,
            R.animator.slide_bottom_down,
            R.animator.slide_bottom_up,
            R.animator.slide_bottom_down)
}

fun FragmentTransaction.slideInRight() {
    setCustomAnimations(
            R.animator.slide_in_right,
            R.animator.slide_out_right,
            R.animator.slide_in_right,
            R.animator.slide_out_right
    )
}

fun FragmentTransaction.replaceWithSlideInRight(fragment: Fragment, tag: String) {
    replace(R.id.content_frame, fragment, tag).commitAllowingStateLoss()
}

fun FragmentTransaction.replaceWithSlideInBottom(fragment: Fragment, tag: String) {
    replace(R.id.content_frame, fragment, tag).commitAllowingStateLoss()
}

fun FragmentTransaction.replaceNotBackStack(fragment: Fragment, tag: String) =
        replace(R.id.content_frame, fragment, tag).commitAllowingStateLoss()

fun FragmentTransaction.addWithAddBackStack(fragment: Fragment, tag: String) {
    slideInRight()
    add(R.id.content_frame, fragment, tag).addToBackStack(tag).commitAllowingStateLoss()
}

fun FragmentTransaction.addWith(fragment: Fragment, tag: String) =
        add(R.id.content_frame, fragment, tag).commitAllowingStateLoss()
