package com.testdevlab.besttactoe

import androidx.compose.runtime.Composable

@Composable
expect fun getSystemAnimatorScale(): Float

/** //wrong
 * if sys.speed is 1.5x faster than normal
 * then we multiply provided val with 1.5f
 */
@Composable
fun Long.handleDeviceSetSpeed(): Long = (this * getSystemAnimatorScale()).toLong()
@Composable
fun Int.handleDeviceSetSpeed(): Int {
    return (this / getSystemAnimatorScale()).toInt()
}
@Composable
fun Float.handleDeviceSetSpeed(): Float = this * getSystemAnimatorScale()
