package com.graime.streetvoice.modules.resources

import android.content.Context
import android.support.v4.content.ContextCompat
import java.io.File
import java.util.*

class ResourcesHelper(private val context: Context) {

    fun getString(stringId: Int): String = context.getString(stringId)

    fun getPluralString(stringId: Int, count: Int): String = context.resources.getQuantityString(stringId, count, count)

    fun getString(stringId: Int, vararg formatArgs: Any): String = context.getString(stringId, *formatArgs)

    fun getStringArray(stringId: Int): Array<String> = context.resources.getStringArray(stringId)

    fun getCacheDir(): File = context.cacheDir

    fun getColor(color: Int): Int = ContextCompat.getColor(context, color)
}