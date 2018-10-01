package com.smartgrid.mobileres10kv.screens.line

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.graime.streetvoice.screens.base.ui.fragments.BaseFragment
import com.smartgrid.mobileres10kv.R
import kotlinx.android.synthetic.main.fragment_list.*

class ListLineFragment : BaseFragment(), ListLineView {

    @InjectPresenter
    lateinit var presenter: ListLinePresenter

    companion object {
        const val TAG = "LIST_LINE_FRAGMENT"

        fun instance(): ListLineFragment {
            val fragment = ListLineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun tag(): String {
        return TAG
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length > 1) {
                    presenter.search(p0.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        button?.setOnClickListener {
            editText.text.clear()
        }
    }

}