package com.kou.promilling

import org.junit.Assert
import org.junit.Test

class CalculationFuncsTests {
    @Test
    fun calculateSpiralContactTest() {
        Assert.assertNotEquals(
            Double.NaN,
            calculateSpiralContact(
                diameter = 20.0,
                spiralAngle = 45.9,
                height = 5.5,
                width = 3.2,
                fluteCount = 4,
                flutePosition = 120.05
            )
        )
    }

    @Test
    fun calculateCuttingWidthTest() {
        Assert.assertNotEquals(
            Double.NaN,
            calculateCuttingWidth(
                r1 = 5.0,
                r = 10.1,
                ae = 5.2
            )
        )
    }

    @Test
    fun calculateTrochoidWidthTest() {
        Assert.assertNotEquals(
            Double.NaN,
            calculateTrochoidWidth(
                r1 = 5.0,
                r = 10.6,
                ae = 0.2
            )
        )
    }
}