package com.graime.streetvoice.screens.base.ui.viewholder

import android.view.View
import android.widget.TextView
import com.graime.streetvoice.screens.base.ui.listeners.HeaderClickListener
import com.graime.streetvoice.screens.base.ui.item.HeaderItem
import com.smartgrid.mobileres10kv.R

class HeaderViewHolder(view: View, clickListener: HeaderClickListener? = null)
    : BaseViewHolder<HeaderItem, HeaderClickListener>(view, clickListener) {

    var textView: TextView? = view.findViewById(R.id.textView)
    var buttonTextView: TextView? = view.findViewById(R.id.btnText)

    override fun bind(item: HeaderItem) {
        super.bind(item)

        textView?.text = item.titleText
        if (item.buttonText.isNullOrEmpty())
            buttonTextView?.visibility = View.GONE
        else {
            buttonTextView?.visibility = View.VISIBLE
            buttonTextView?.text = item.buttonText
        }
    }

    init {
        buttonTextView?.setOnClickListener { clickListener?.onHeaderButtonClick(item?.id.orEmpty()) }
    }
}