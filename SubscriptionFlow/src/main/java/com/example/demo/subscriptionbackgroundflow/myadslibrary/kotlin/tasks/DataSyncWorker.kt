package com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.tasks

import android.content.Context
import android.os.Build
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.api.ApiHelper
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.api.RetrofitBuilder
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.AppDatabase
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.entity.AppEntity
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.db.entity.IdEntity
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.helper.NativeHelper
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model.AppModel
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model.ResponseID
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.model.ResponseModel
import com.example.demo.subscriptionbackgroundflow.myadslibrary.kotlin.repository.MainRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DataSyncWorker(val appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    private var mRequestQueue: RequestQueue? = null

    companion object {
        private const val TAG = "DataSyncWorker"
    }

    override suspend fun doWork(): Result = coroutineScope {

        val db = AppDatabase.getInstance(appContext)
        val mainRepository: MainRepository? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            MainRepository(ApiHelper(RetrofitBuilder.apiService))
        else
            null

        val jobs = async {
            var model: ResponseModel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mainRepository?.getData()
            } else {
                getData()
            }

            if (model != null && model.status == "1" && model.message == "success") {

                db.AppDao().deleteAll()
                db.query(SimpleSQLiteQuery("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'AppEntity'"))
//                db.AppDao().clearPrimaryKey()
                model.data?.forEach {
                    it?.let {
                        db.AppDao().insert(AppEntity(
                                it.position!!,
                                it.name!!,
                                it.app_link!!,
                                it.image!!,
                                it.is_trending!!
                        ))
                    }

                    /* val mo: AppEntity? = db.AppDao().getApp(it.app_link)
                     mo?.let { s ->
                         if (is_equals(s, it)) {
                             db.AppDao().update(AppEntity(
                                     0,
                                     it.position,
                                     it.name,
                                     it.app_link,
                                     it.image,
                                     it.is_trending
                             ))
                         }
                     } ?: db.AppDao().insert(AppEntity(
                             0,
                             it.position,
                             it.name,
                             it.app_link,
                             it.image,
                             it.is_trending
                     ))*/
                }
            } else {
                throw RuntimeException("Fetching data failed")
            }

        }

        val jobs1 = async {
            val model: ResponseID? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mainRepository?.getIds()
            } else {
                getDataId()
            }

            if (model != null && model.status == "1" && model.message == "success") {

                db.IdDao().deleteAll()
//                db.IdDao().clearPrimaryKey()
                db.query(SimpleSQLiteQuery("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = 'IdEntity'"))
                model.data?.forEach { dataitem ->
                    dataitem?.let {
                        it.advertisement?.forEach { ad_list ->
                            ad_list.let {
                                it?.token?.forEach {
                                    val model = IdEntity(
                                            0,
                                            dataitem.id!!,
                                            dataitem.name!!,
                                            ad_list!!.adCategoryId!!,
                                            ad_list!!.adCategoryName!!,
                                            it.google,
                                            it.facebook
                                    )
                                    db.IdDao().insert(model)
                                }
                            }
                        }
                    }


                }
            } else {
                throw RuntimeException("Fetching data failed")
            }

        }

        try {
            jobs1.await()
            jobs.await()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    private suspend fun getData() = suspendCoroutine<ResponseModel?> {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(appContext)

        val req = JsonObjectRequest(Request.Method.GET, NativeHelper().getAppBaseUrl() + "more_app", null,
                { response ->
                    if (response != null) {
                        try {
                            val obj: ResponseModel? = Gson().fromJson(response.toString(), ResponseModel::class.java)
                            it.resume(obj)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            it.resume(null)
                        }
                    } else {
                        it.resume(null)
                    }
                },
                { error ->
                    VolleyLog.e("Error: ", error.message)
                    it.resume(null)
                }
        )

        mRequestQueue?.add(req)
    }

    private suspend fun getDataId() = suspendCoroutine<ResponseID?> {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(appContext)

        val req = JsonObjectRequest(Request.Method.GET, NativeHelper().getAppBaseUrl() + "advertisement_list", null,
                { response ->
                    if (response != null) {
                        try {
                            val obj: ResponseID? = Gson().fromJson(response.toString(), ResponseID::class.java)
                            it.resume(obj)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            it.resume(null)
                        }
                    } else {
                        it.resume(null)
                    }
                },
                { error ->
                    VolleyLog.e("Error: ", error.message)
                    it.resume(null)
                }
        )

        mRequestQueue?.add(req)
    }

    private fun is_equals(it: AppEntity, model: AppModel): Boolean {
        if (it.position != model.position) {
            return false
        }
        if (it.name != model.name) {
            return false
        }
        if (it.app_link != model.app_link) {
            return false
        }
        if (it.image != model.image) {
            return false
        }
        if (it.is_trending != model.is_trending) {
            return false
        }
        return true
    }
}