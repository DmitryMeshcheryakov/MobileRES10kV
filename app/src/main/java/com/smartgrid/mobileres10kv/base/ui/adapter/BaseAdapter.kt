package com.graime.streetvoice.screens.base.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.graime.streetvoice.screens.base.ui.item.BaseItem
import com.graime.streetvoice.screens.base.ui.item.HeaderItem
import com.graime.streetvoice.screens.base.ui.listeners.BaseItemClickListener
import com.graime.streetvoice.screens.base.ui.listeners.HeaderClickListener
import com.graime.streetvoice.screens.base.ui.viewholder.BaseViewHolder
import com.graime.streetvoice.screens.base.ui.viewholder.EmptyViewHolder
import com.graime.streetvoice.screens.base.ui.viewholder.HeaderViewHolder
import com.smartgrid.mobileres10kv.R


abstract class BaseAdapter(val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = -1
        const val VIEW_TYPE_EMPTY = -2
        const val VIEW_TYPE_EVENT = -3
        const val VIEW_TYPE_PROFILE = -4
        const val VIEW_TYPE_LOCATION = -5
        const val VIEW_TYPE_COMMENT = -6
        const val VIEW_TYPE_RECORD = -7
        const val VIEW_TYPE_ACTIV = -8
        const val VIEW_TYPE_MISSION = -9
    }

    private val inflater = LayoutInflater.from(context)!!

    var scrollBottomListener: (() -> Unit)? = null

    var headerClickListener: HeaderClickListener? = null

    var emptyClickListener: BaseItemClickListener? = null

    var items: List<BaseItem> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun generateView(layoutId: Int, parent: ViewGroup)
            = inflater.inflate(layoutId, parent, false)!!

    fun addItems(list: List<BaseItem>, notify: Boolean = true) {
        val count = items.size
        (items as? ArrayList?)?.addAll(list)
        if (notify)
            notifyItemRangeInserted(count, list.size)
    }

    fun clearItems(notify: Boolean = true) {
        items = ArrayList()
        if (notify)
            notifyDataSetChanged()
    }

    fun addHeader(title: String, id: String = "", btnText: String? = null, notify: Boolean = true) {
        var count = items.size
        (items as? ArrayList?)?.add(HeaderItem(id, title, btnText))
        if (notify)
            notifyItemInserted(++count)
    }

    fun showEmpty(text: String? = context?.getString(R.string.empty), notify: Boolean = true) {
        clearItems(false)
        (items as? ArrayList?)?.add(BaseItem(VIEW_TYPE_EMPTY, "", text.orEmpty()))
        if (notify)
            notifyDataSetChanged()
    }

    fun updateItem(id: String) {
        items.firstOrNull { it.id == id }?.let {  notifyItemChanged(items.indexOf(it)) }
    }

    fun findItemPosById(id: String) : Int {
        items.firstOrNull { it.id == id }?.let {
            return items.indexOf(it)
        }
        return -1
    }

    abstract fun createVH(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = when(viewType) {
                VIEW_TYPE_HEADER -> HeaderViewHolder(generateView(R.layout.item_header, parent), headerClickListener)
                VIEW_TYPE_EMPTY -> EmptyViewHolder(generateView(R.layout.item_empty, parent), emptyClickListener)
                else -> createVH(parent, viewType)
    }

    override fun getItemCount(): Int
            = items.size

    override fun getItemViewType(position: Int): Int
            = items[position].viewType

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder<BaseItem, BaseItemClickListener>)?.bind(items[position])
        if (position > items.size - 5)
            scrollBottomListener?.invoke()
    }
}