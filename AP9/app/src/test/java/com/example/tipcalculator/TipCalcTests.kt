package com.example.tipcalculator
import org.junit.Test
import org.junit.Assert.assertEquals
import java.text.NumberFormat

class TipCalcTests {

    @Test
    fun calculateTip_20percentTest(){
        val amount: Double = 10.0
        val percent: Double = 20.0
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = TipCalc(amount = amount, tipPercent = percent, false)
        assertEquals(expectedTip, actualTip)
    }
}