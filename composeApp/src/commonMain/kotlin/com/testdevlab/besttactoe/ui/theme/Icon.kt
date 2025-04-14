package com.testdevlab.besttactoe.ui.theme

import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_circle
import besttactoe.composeapp.generated.resources.ic_cross
import org.jetbrains.compose.resources.DrawableResource

enum class Icon(val resource: DrawableResource) {
    Cross(resource = Res.drawable.ic_cross),
    Circle(resource = Res.drawable.ic_circle)
}