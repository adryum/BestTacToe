package com.testdevlab.besttactoe

import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun getSystemAnimatorScale(): Float {
    val context = LocalContext.current
    return try {
        Settings.Global.getFloat(
            context.contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE
        )
    } catch (e: Settings.SettingNotFoundException) {
        1f
    }
}