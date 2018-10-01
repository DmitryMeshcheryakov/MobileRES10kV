package com.graime.streetvoice.screens.base.ui.viewholder

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.graime.streetvoice.common.utils.Utils
import com.graime.streetvoice.screens.base.ui.item.BaseItem
import com.graime.streetvoice.screens.base.ui.listeners.BaseItemClickListener
import com.smartgrid.mobileres10kv.R

abstract class BaseViewHolder<I : BaseItem, L : BaseItemClickListener>(itemView: View, var itemClickListener: L? = null)
    : RecyclerView.ViewHolder(itemView) {

    var item: I? = null

    open fun bind(item : I) {
        this.item = item
    }

    init {
        itemView.setOnClickListener { _ ->
            item?.let { itemClickListener?.onItemClick(it) }
        }
    }

    fun loadImage(imageView: ImageView?, uri: String) {
        if (!uri.isEmpty())
            imageView?.let {
//                GlideApp.with(itemView)
//                        .load(uri)
//                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                        .skipMemoryCache(true)
//                        .into(it)
//                        .onLoadStarted(ContextCompat.getDrawable(itemView.context, R.drawable.preload_bg))
            }
    }
}