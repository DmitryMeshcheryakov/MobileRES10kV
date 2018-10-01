package com.smartgrid.mobileres10kv.screens

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.graime.streetvoice.screens.base.ui.activity.BaseActivity
import com.smartgrid.mobileres10kv.R
import com.smartgrid.mobileres10kv.screens.line.ListLineFragment
import com.smartgrid.mobileres10kv.screens.start.StartFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(StartFragment.instance())
    }

    fun showListLineScreen() {
        addFragment(ListLineFragment.instance())
    }
}
