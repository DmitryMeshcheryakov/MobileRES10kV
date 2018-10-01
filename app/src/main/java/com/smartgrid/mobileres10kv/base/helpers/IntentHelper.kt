package com.graime.streetvoice.screens.base.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.smartgrid.mobileres10kv.common.Constants


object IntentHelper {

    private const val MIME_TYPE = "image/*"

    fun isActivityResolved(activity: Activity, intent: Intent): Boolean = intent.resolveActivity(activity.packageManager) != null

    fun openGalleryScreenForSelectImage(activity: AppCompatActivity) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"))
        intent.action = Intent.ACTION_GET_CONTENT

        if (!TextUtils.isEmpty(MIME_TYPE)) {
            intent.type = MIME_TYPE
        }

        activity.startActivityForResult(intent, Constants.RC_HANDLE_GALLERY)
    }

    fun openGalleryScreen(activity: Activity, mimeType: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"))
        intent.action = Intent.ACTION_GET_CONTENT

        if (!TextUtils.isEmpty(mimeType)) {
            intent.type = mimeType
        }

        activity.startActivityForResult(intent, Constants.RC_HANDLE_GALLERY)
    }

    fun openBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        val title = "Choose browser"
        val chooser = Intent.createChooser(intent, title)
        context.startActivity(chooser)
    }

    fun share(activity: Context, text: String?, subject: String?) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"

        if (subject != null)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)

        if (text != null)
            emailIntent.putExtra(Intent.EXTRA_TEXT, text)

        try {
            activity.startActivity(Intent.createChooser(emailIntent, "Share"))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(activity, "There are no email clients installed.",
                    Toast.LENGTH_SHORT).show()
        }
    }

    fun openAppSettings(context: Context?) {
        try {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", context?.packageName, null)
            intent.data = uri
            context?.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}