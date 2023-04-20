package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.google.common.util.concurrent.ListenableFuture
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.tasks.DataSyncWorker
import com.example.demo.subscriptionbackgroundflow.myadslibrary.utils.InternetConnection
import java.util.concurrent.ExecutionException


class Helper {
    companion object{
        var mDrawable : Drawable? = null
    }

    fun isScheduled(context: Context?): Boolean {

        // NOT WORKS!
        val instance = WorkManager.getInstance(context!!)
        val statuses: ListenableFuture<List<WorkInfo>> = instance
                .getWorkInfosByTag("SyncData")
        return try {
            var running = false
            val workInfoList: List<WorkInfo>
            workInfoList = statuses.get()
            for (workInfo in workInfoList) {
                val state = workInfo.state
                running = state == WorkInfo.State.RUNNING ||
                        state == WorkInfo.State.ENQUEUED
            }
            running
        } catch (e: ExecutionException) {
            e.printStackTrace()
            false
        } catch (e: InterruptedException) {
            e.printStackTrace()
            false
        }
    }

    fun startDataSync(context: Context,life : LifecycleOwner) {
        if(!InternetConnection.checkConnection(context))
            return

        if(isScheduled(context))
            return

        val uploadWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(DataSyncWorker::class.java)
                .addTag("SyncData")
                .build()
        WorkManager
                .getInstance(context)
                .enqueue(uploadWorkRequest)


        WorkManager.getInstance(context).getWorkInfosByTagLiveData("SyncData")
                .observe(life, Observer {
                    if (it != null && it[0].state.isFinished) {
                    }
                })
    }

    interface OnDataLoaded {
        fun onLoad()
    }
}