package com.habits.models

data class AppBuildInfo(
    val debug: Boolean,
    val buildType: String,
    val flavor: String,
    val versionCode: Int,
    val versionName: String
)
