package com.kou.promilling

import java.lang.Math.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.tan

const val FULL_ROTATION_DEGREES = 360.0

/**
 * Calculated spiral contact length.
 */
fun calculateSpiralContact(
    diameter: Double,
    spiralAngle: Double,
    height: Double,
    width: Double,
    fluteCount: Int,
    flutePosition: Double
): Double {
    var toothCount = fluteCount
    var toothAngle = flutePosition
    val teethStep = FULL_ROTATION_DEGREES / toothCount
    var result = 0.0
    do {
        result += countLengthBase(diameter, spiralAngle, height, width, toothAngle)
        toothAngle += teethStep
        if (toothAngle > FULL_ROTATION_DEGREES) toothAngle -= FULL_ROTATION_DEGREES
        toothCount -= 1
    } while (toothCount > 0)

    return result
}

/**
 * Calculated cutting width.
 */
fun calculateCuttingWidth(r1: Double, r: Double, ae: Double): Double {
    val eps = 0.00005
    var a = acos((r1 - ae) / r1)
    var b = acos(-1.0)
    var c: Double
    var n = 0
    do {
        n++
        c = (a + b) / 2
        if (cuttingWidthRadF(r1, r, ae, a) * cuttingWidthRadF(r1, r, ae, c) < 0) {
            b = c
        } else {
            a = c
        }
        if (n > 200) {
            break
        }
    } while (b - a > eps)

    c = (a + b) / 2
    if (kotlin.math.sin(c) * r1 < r1 + r - ae && ae > r) {
        c = acos((r1 + r - ae) / r1) + kotlin.math.asin(1.0)
    }

    return r1 - cos(c) * r1
}

/**
 * Calculated trochoid width.
 */
fun calculateTrochoidWidth(r1: Double, r: Double, ae: Double): Double {
    val eps = 0.00005
    var a = acos((r1 - ae) / r1)
    var b = acos(-1.0) / 2
    var c: Double
    var c1: Double
    var n = 0
    do {
        n++
        c = (a + b) / 2
        if (trochoidWidthRadFF(r1, r, ae, a) * trochoidWidthRadFF(r1, r, ae, c) < 0) {
            b = c
        } else {
            a = c
        }
        if (n > 200) {
            break
        }
    } while (b - a > eps)

    c = (a + b) / 2
    n = 0
    a = 0.0
    b = acos(-1.0)
    do {
        n++
        c1 = (a + b) / 2
        if (trochoidWidthRadF(r1, r, ae, a) * trochoidWidthRadF(r1, r, ae, c1) < 0) {
            b = c1
        } else {
            a = c1
        }
        if (n > 200) {
            break
        }
    } while (b - a > eps)

    c1 = (a + b) / 2
    if (c1 > c) {
        c = c1
    }

    return r1 - cos(c) * r1
}

private fun countLengthBase(
    diameter: Double,
    spiralAngle: Double,
    height: Double,
    width: Double,
    toothAngle: Double
): Double {
    val radius = diameter / 2

    var caseE8 = false
    var caseF8 = false
    var caseG8 = false
    var caseH8 = false
    var caseJ8 = false
    var caseK8 = 0.0
    var caseL8 = 0.0
    var caseM8 = 0.0
    var caseN8 = 0.0
    val caseO8 = 0.0
    var caseP8 = 0.0

    val a6 = toDegrees(acos((radius - width) / radius))
    val f4 = tan(toRadians(spiralAngle))
    val g4 = (height / f4 + 0 * PI * diameter / FULL_ROTATION_DEGREES) * FULL_ROTATION_DEGREES / (PI * diameter)
    val fullSpiralLength: Double = PI * diameter * f4
    val b8 = if (toothAngle + g4 < FULL_ROTATION_DEGREES) {
        toothAngle + g4
    } else {
        toothAngle + g4 - FULL_ROTATION_DEGREES
    }

    if ((toothAngle < a6) and (b8 <= a6) and (b8 < toothAngle)) caseE8 = true
    if ((toothAngle <= a6) and (b8 <= a6) and (b8 > toothAngle)) caseF8 = true
    if ((toothAngle < a6) and (b8 >= a6)) caseG8 = true
    if ((toothAngle >= a6) and (b8 <= a6)) caseH8 = true
    if ((toothAngle >= a6) and (b8 >= a6) and (b8 < toothAngle)) caseJ8 = true
    if (caseE8) caseK8 = b8 + a6 - toothAngle
    if (caseF8) caseL8 = b8 - toothAngle
    if (caseG8) caseM8 = a6 - toothAngle
    if (caseH8) caseN8 = b8
    if (caseJ8) caseP8 = a6
    val contAngle = if (height == fullSpiralLength) {
        a6
    } else {
        caseK8 + caseL8 + caseM8 + caseN8 + caseO8 + caseP8
    }

    val c8 = contAngle * diameter * PI / FULL_ROTATION_DEGREES
    val c6 = cos(toRadians(spiralAngle))
    val result = c8 / c6

    return if (fullSpiralLength < height) {
        val fullSpiralCounts = (height / fullSpiralLength).toInt()
        val restOfSpiral = height % fullSpiralLength

        countLengthBase(
            diameter, spiralAngle, fullSpiralLength,
            width, toothAngle
        ) * fullSpiralCounts + countLengthBase(
            diameter, spiralAngle, restOfSpiral,
            width, toothAngle
        )
    } else {
        result
    }
}

private fun cuttingWidthRadF(r1: Double, r: Double, ae: Double, x: Double): Double {
    return (r1 + r) * kotlin.math.sin(acos((ae + r + r1 * cos(x)) / (r + r1))) - ae - r1 *
            kotlin.math.sin(x)
}

private fun trochoidWidthRadF(r1: Double, r: Double, ae: Double, x: Double): Double {
    return (r1 + r) * kotlin.math.sin(acos((r + r1 * cos(x)) / (r + r1))) - r1 *
            kotlin.math.sin(x) - ae
}

private fun trochoidWidthRadFF(r1: Double, r: Double, ae: Double, x: Double): Double {
    return (r1 + r) * kotlin.math.sin(acos((ae + r + r1 * cos(x)) / (r + r1))) - r1 *
            kotlin.math.sin(x)
}