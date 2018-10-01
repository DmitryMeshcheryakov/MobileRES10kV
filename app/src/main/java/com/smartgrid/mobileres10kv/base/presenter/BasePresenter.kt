package com.graime.streetvoice.screens.base.presenter

import android.Manifest
import android.content.Context
import android.support.annotation.CallSuper
import android.support.v4.app.FragmentActivity
import android.widget.ImageView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpPresenter
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.graime.streetvoice.modules.location.LocationHelper
import com.graime.streetvoice.modules.preferences.PreferencesHelper
import com.graime.streetvoice.modules.resources.ResourcesHelper
import com.graime.streetvoice.screens.base.ui.activity.BaseActivity
import com.graime.streetvoice.screens.base.ui.view.BaseView
import com.smartgrid.mobileres10kv.App
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var resources: ResourcesHelper

    @Inject
    lateinit var preferences: PreferencesHelper

    @Inject
    lateinit var location: LocationHelper

    private val requestManager: RequestManager? = null

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        App.component?.inject(this as BasePresenter<BaseView>)
        App.bus.register(this)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        App.bus.unregister(this)
    }

    fun loadImageWithGlideImagePlaceholder(imageView: ImageView,
                                           imageUrl: String,
                                           imagePlaceHolder: Int,
                                           errorPlaceHolder: Int) {
        val requestBuilder = requestManager?.load(imageUrl)
        val requestOptions = RequestOptions()
        requestOptions.placeholder(imagePlaceHolder)
        requestOptions.error(errorPlaceHolder)
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
        requestBuilder?.apply(requestOptions)
        requestBuilder?.into(imageView)
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun checkPermissions(activity: FragmentActivity?, vararg permissions: String?, onSuccess: (()->Unit)? = null) {
        activity?.let {
            val request: Observable<Boolean> = if (permissions.isEmpty())
                RxPermissions(it).request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
            else
                RxPermissions(it).request(*permissions)

            request.subscribe { granted ->
                    if (!granted) {
                        viewState?.showNotPermitted()
                    } else {
                        onSuccess?.invoke()
                    }
            }
        }
    }
}