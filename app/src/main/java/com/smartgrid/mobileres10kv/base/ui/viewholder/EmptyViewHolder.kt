package com.graime.streetvoice.screens.base.ui.viewholder

import android.view.View
import android.widget.TextView
import com.graime.streetvoice.screens.base.ui.item.BaseItem
import com.graime.streetvoice.screens.base.ui.listeners.BaseItemClickListener
import com.smartgrid.mobileres10kv.R

class EmptyViewHolder(view: View, clickListener: BaseItemClickListener? = null)
    : BaseViewHolder<BaseItem, BaseItemClickListener>(view, clickListener) {

    var textView: TextView? = itemView?.findViewById(R.id.textView)

    override fun bind(item: BaseItem) {
        super.bind(item)
        textView?.text = item.titleText
    }
}