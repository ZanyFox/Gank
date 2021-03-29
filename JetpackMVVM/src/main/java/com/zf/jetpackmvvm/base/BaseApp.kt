package com.zf.jetpackmvvm.base

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri


/**
 * ContentProvider 在应用初始化后初始化
 * 通过ContentProvider获取Application
 */
val appContext: Application by lazy { BaseApp.INSTANCE }

class BaseApp : ContentProvider() {

    companion object {

         lateinit var INSTANCE: Application
    }

    private fun install(application: Application) {
        INSTANCE = application
    }

    override fun onCreate(): Boolean {
        val application = context!!.applicationContext as Application
        install(application)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0


}