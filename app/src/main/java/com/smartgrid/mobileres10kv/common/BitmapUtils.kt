package com.graime.streetvoice.common.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import java.io.*
import java.nio.ByteBuffer
import android.graphics.*
import android.media.ThumbnailUtils
import android.support.v4.content.ContextCompat
import com.smartgrid.mobileres10kv.R
import com.smartgrid.mobileres10kv.common.Constants
import java.net.HttpURLConnection
import java.net.URL


object BitmapUtils {

    fun getUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "", null)
        return Uri.parse(path)
    }

    fun getBitmapFromIntent(requestCode: Int, data: Intent, context: Context): Bitmap? {
        var bitmap: Bitmap? = null
        if (requestCode == Constants.RC_GET_CAMERA_PHOTO) {
            if (data.extras != null && data.extras!!.get("data") != null) {
                bitmap = data.extras!!.get("data") as Bitmap
            }
        } else if (requestCode == Constants.RC_HANDLE_GALLERY) {
            val uri = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } catch (ignored: IOException) {
            }

        }

        return bitmap
    }

    fun getBitmapFromUri(uri: Uri?, context: Context): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } catch (e: IOException) {
            null
        }

    }

    fun bitmapToFile(bitmap: Bitmap, filePath: String): Boolean {
        var fos: FileOutputStream? = null

        try {
            //onCreate a file to write bitmap data
            val file = File(filePath)
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)

            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()

            return true
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        return false
    }

    fun saveBitmapToAppDir(applicationContext: Context, bitmap: Bitmap, fileName: String): String? {
        val filePath = String.format("%s/%s", applicationContext.filesDir.path, fileName)
        return if (BitmapUtils.bitmapToFile(bitmap, filePath)) {
            filePath
        } else null
    }


    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        var buffer: ByteArrayOutputStream? = null
        try {
            buffer = ByteArrayOutputStream(bitmap.width * bitmap.height)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer)
            return buffer.toByteArray()
        } finally {
            if (buffer != null) {
                try {
                    buffer.close()
                } catch (ignored: IOException) {

                }

            }
        }
    }

    fun getBitmapFromURL(src: String): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url
                    .openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun convertBitmapToByteArrayUncompressed(bitmap: Bitmap): ByteArray {
        val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
        bitmap.copyPixelsToBuffer(byteBuffer)
        byteBuffer.rewind()
        return byteBuffer.array()
    }

    fun convertCompressedByteArrayToBitmap(src: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(src, 0, src.size)
    }

    fun decodeBase64(input: String): Bitmap {
        val decodedBytes = Base64.decode(input, 0)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun encodeToBase64(image: Bitmap, compressFormat: Bitmap.CompressFormat, quality: Int): String {
        val byteArrayOS = ByteArrayOutputStream()
        image.compress(compressFormat, quality, byteArrayOS)
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP)
    }

    fun getBase64(bitmap: Bitmap?): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun addRoundedCorner(bitmap: Bitmap, width: Float, color: Int) : Bitmap {
        val w = bitmap.width
        val h = bitmap.height

        val radius = Math.min(h / 2, w / 2).toFloat()
        val output = Bitmap.createBitmap(w + width.toInt()*2, h + width.toInt()*2, Bitmap.Config.ARGB_8888)

        val p = Paint()
        p.isAntiAlias = true

        val c = Canvas(output)
        c.drawARGB(0, 0, 0, 0)
        p.style = Paint.Style.FILL

        c.drawCircle(w / 2f + width, h / 2f + width, radius, p)

        p.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        c.drawBitmap(bitmap, width, width, p)
        p.xfermode = null
        p.style = Paint.Style.STROKE
        p.color = color
        p.strokeWidth = width
        c.drawCircle(w / 2 + width, h / 2 + width, radius, p)

        return output
    }

    fun rotateImage(src: Bitmap?, degree: Float): Bitmap? {
        src?.let {
            val matrix = Matrix()
            matrix.postRotate(degree)
            return Bitmap.createBitmap(src, 0, 0, it.width, it.height, matrix, true)
        }
        return null
    }

    fun createMarker(context: Context, bitmap: Bitmap?, size: Int, color: Int, withDot: Boolean = true) : Bitmap {
        var result = if (bitmap != null)
            ThumbnailUtils.extractThumbnail(bitmap, size, size, ThumbnailUtils.OPTIONS_RECYCLE_INPUT)
        else {
            val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            val c = Canvas(output)
            val col = ContextCompat.getColor(context, R.color.colorAccent)
            c.drawRGB(col shr 16 and 0xFF, col shr 8 and 0xFF, col shr 0 and 0xFF)
            output
        }


        result = addRoundedCorner(result, (size / 15).toFloat(), Color.WHITE)
        result = addRoundedCorner(result, (size / 10).toFloat(), color)
        if (withDot) {
            val newSize = (size + (size / 5) + (size / 7.5)).toInt()
            val output = Bitmap.createBitmap(newSize, newSize + (newSize / 5) + (size / 3), Bitmap.Config.ARGB_8888)

            val p = Paint()
            val c = Canvas(output)
            c.drawARGB(0, 0, 0, 0)
            c.drawBitmap(result, 0f, 0f, p)
            p.xfermode = null
            p.style = Paint.Style.FILL
            p.color = color
            c.drawCircle((newSize / 2).toFloat(), ((newSize + (newSize / 3)) - (newSize / 5)).toFloat(), (newSize / 15).toFloat(), p)

            return output
        }

        return result
    }


    fun resize(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        var image = bitmap
        if (maxHeight > 0 && maxWidth > 0) {
            val width = image.width
            val height = image.height
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

            var finalWidth = maxWidth
            var finalHeight = maxHeight
            if (ratioMax > 1) {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            } else {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
            return image
        } else {
            return image
        }
    }

    fun sizeOf(data: Bitmap): Int {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            data.byteCount
        } else {
            data.allocationByteCount
        }
    }

    fun makeBitmapForSend(bitmap: Bitmap?): Bitmap {
        val bitmapWork = resize(bitmap!!, 1024, 1024)
        val bytes = ByteArrayOutputStream()
        bitmapWork.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
        return bitmapWork
    }

    fun makeBitmapForSendSmall(bitmap: Bitmap?): Bitmap {
        val bitmapWork = resize(bitmap!!, 1024, 512)
        val bytes = ByteArrayOutputStream()
        bitmapWork.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
        return bitmapWork
    }

    @Throws(IOException::class)
    fun getFileEncoded(file: File): String {
        var ous: ByteArrayOutputStream? = null
        var ios: InputStream? = null
        try {
            val buffer = ByteArray(4096)
            ous = ByteArrayOutputStream()
            ios = FileInputStream(file)

            var read = 0
            read = ios.read(buffer)
            while (read != -1) {
                ous.write(buffer, 0, read)
            }

        } finally {
            ous?.close()
            ios?.close()
        }
        return Base64.encodeToString(ous!!.toByteArray(), Base64.NO_WRAP)
    }

}