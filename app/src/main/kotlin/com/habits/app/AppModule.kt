package com.habits.app

import android.content.Context
import android.content.pm.ApplicationInfo
import com.habits.models.AppBuildInfo
import com.habits.app.overview.detailModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal val appModule = module {
    single { provideAppBuildInfo(context = androidContext()) }
}

private fun provideAppBuildInfo(context: Context): AppBuildInfo = AppBuildInfo(
    debug = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0,
    buildType = BuildConfig.BUILD_TYPE,
    flavor = BuildConfig.FLAVOR,
    versionCode = BuildConfig.VERSION_CODE,
    versionName = BuildConfig.VERSION_NAME
)

internal val appModules: List<Module> = listOf(appModule, detailModule)
