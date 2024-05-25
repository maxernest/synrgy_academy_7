package com.example.androidapp

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class BlurWorker(context : Context, params : WorkerParameters): Worker(context,params) {

    override fun doWork(): Result {
        val inputData = inputData.getString("inputData")
        if (inputData != null) {
            Log.e("Worker",inputData)
        }
        val outputData = Data.Builder().putString("outputData","this is output data").build()
        return Result.success(outputData)
    }
}