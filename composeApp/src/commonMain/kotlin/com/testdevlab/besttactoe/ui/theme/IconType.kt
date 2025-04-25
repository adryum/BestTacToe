package com.testdevlab.besttactoe.ui.theme

import besttactoe.composeapp.generated.resources.Res
import besttactoe.composeapp.generated.resources.ic_circle
import besttactoe.composeapp.generated.resources.ic_circle_block
import besttactoe.composeapp.generated.resources.ic_circle_dashed
import besttactoe.composeapp.generated.resources.ic_circle_four_circles
import besttactoe.composeapp.generated.resources.ic_circle_pixels
import besttactoe.composeapp.generated.resources.ic_circle_square
import besttactoe.composeapp.generated.resources.ic_cross
import besttactoe.composeapp.generated.resources.ic_cross_checkbox
import besttactoe.composeapp.generated.resources.ic_cross_empty
import besttactoe.composeapp.generated.resources.ic_cross_in_circle
import besttactoe.composeapp.generated.resources.ic_cross_joystick
import besttactoe.composeapp.generated.resources.ic_cross_square
import org.jetbrains.compose.resources.DrawableResource

interface IIcon {
    val resource: DrawableResource
}

enum class CircleType(override val resource: DrawableResource) : IIcon {
    Circle(Res.drawable.ic_circle),
    CircleDashed(Res.drawable.ic_circle_dashed),
    CircleFourCircles(Res.drawable.ic_circle_four_circles),
    CirclePixels(Res.drawable.ic_circle_pixels),
    CircleSquare(Res.drawable.ic_circle_square),
    CircleBlock(Res.drawable.ic_circle_block),
}

enum class CrossType(override val resource: DrawableResource) : IIcon {
    Cross(Res.drawable.ic_cross),
    CrossEmpty(Res.drawable.ic_cross_empty),
    CrossInCircle(Res.drawable.ic_cross_in_circle),
    CrossJoystick(Res.drawable.ic_cross_joystick),
    CrossSquare(Res.drawable.ic_cross_square),
    CrossCheckbox(Res.drawable.ic_cross_checkbox),
}

fun getRandomIconRes(): DrawableResource {
    val iconList =
        if ((0..1).random() == 1) CircleType.entries
        else CrossType.entries

    return iconList[(0..<iconList.size).random()].resource
}