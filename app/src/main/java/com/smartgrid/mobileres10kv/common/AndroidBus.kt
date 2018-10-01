package com.graime.streetvoice.common.utils

import android.os.Handler
import android.os.Looper
import com.squareup.otto.Bus

class AndroidBus : Bus() {
    private val mHandler = Handler(Looper.getMainLooper())

    override fun post(event: Any) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event)
        } else {
            mHandler.post { super@AndroidBus.post(event) }
        }
    }
}