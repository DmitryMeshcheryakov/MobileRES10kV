package com.smartgrid.mobileres10kv.screens.start

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.graime.streetvoice.screens.base.ui.fragments.BaseFragment
import com.smartgrid.mobileres10kv.R
import com.smartgrid.mobileres10kv.screens.MainActivity
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : BaseFragment(), StartView {

    @InjectPresenter
    lateinit var presenter: StartPresenter

    companion object {
        const val TAG = "START_FRAGMENT"

        fun instance(): StartFragment {
            val fragment = StartFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun tag(): String {
        return TAG
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnStart.setOnClickListener { (activity as? MainActivity)?.showListLineScreen() }
    }
}