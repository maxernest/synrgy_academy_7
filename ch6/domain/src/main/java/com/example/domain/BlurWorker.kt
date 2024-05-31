package com.example.domain

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.example.domain.utils.blurBitmap
import com.example.domain.utils.writeBitmapToFile

private const val TAG = "BlurWorker"

class BlurWorker(context : Context, params : androidx.work.WorkerParameters): androidx.work.Worker(context,params) {

    override fun doWork(): Result {
        val appContext = applicationContext

        val resourceUri = inputData.getString(com.example.background.KEY_IMAGE_URI)

        return try {
            if (TextUtils.isEmpty(resourceUri)) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }

            val resolver = appContext.contentResolver

            val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )

            val output = blurBitmap(picture, appContext)

            // Write bitmap to a temp file
            val outputUri = writeBitmapToFile(appContext, output)

            val outputData =
                androidx.work.workDataOf(com.example.background.KEY_IMAGE_URI to outputUri.toString())

            Result.success(outputData)
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error applying blur")
            throwable.printStackTrace()
            Result.failure()
        }
    }
}